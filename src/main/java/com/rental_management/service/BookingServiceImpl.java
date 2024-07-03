package com.rental_management.service;


import com.rental_management.dto.*;
import com.rental_management.entities.Booking;
import com.rental_management.entities.Property;
import com.rental_management.entities.User;
import com.rental_management.repo.BookingRepository;
import com.rental_management.repo.PropertyRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;


    public BookingServiceImpl(UserRepository userRepository, PropertyRepository propertyRepository, ModelMapper modelMapper, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;

    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return List.of();
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        return null;
    }

    @Override
    public BookingDTO getById(Long bookingId) {
        return null;
    }

    @Override
    public BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO) {
        return null;
    }

    @Override
    public void deleteById(Long bookingId) {

    }

    @Override
    public ResponseBody createBookingByUserForProperty(Long userId, Long propertyId, List<BookingDTO> bookingList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User not found: " + userId);
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        User optinalUser = existingUser.get();

        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if (existingProperty.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property not found: " + propertyId);
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();

        List<Booking> bookings = bookingList.stream().map(bookingDTO -> {
            Booking booking = modelMapper.map(bookingDTO, Booking.class);

            if (optionalProperty.getMaxOccupancy() < booking.getPeopleNumber()) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("The number of people is higher than maximum occupancy capacity: ");
                errors.add(errorDTO);
                return null;
            }

                booking.setCheckInDate(bookingDTO.getCheckInDate());
                LocalDate checkIn = booking.getCheckInDate();
                LocalDate checkOut = checkIn.plusDays(bookingDTO.getDay());
                booking.setCheckOutDate(checkOut);

            if (optionalProperty.getPromotion() == null) {
                double propertyPrice = optionalProperty.getOriginalPrice() * bookingDTO.getDay();
                booking.setTotalPrice(propertyPrice);
            } else {
                LocalDate checkInDate = booking.getCheckInDate();
                LocalDate checkOutDate = booking.getCheckOutDate();
                LocalDate promotionStartDate = optionalProperty.getPromotion().getStartDate();
                LocalDate promotionEndDate = optionalProperty.getPromotion().getEndDate();

                if (checkOutDate.isBefore(promotionStartDate) || checkInDate.isAfter(promotionEndDate)) {
                    double propertyPrice = optionalProperty.getOriginalPrice() * bookingDTO.getDay();
                    booking.setTotalPrice(propertyPrice);
                } else {
                    double total = 0.0;

                    // Days before the promotion starts
                    if (checkInDate.isBefore(promotionStartDate)) {
                        LocalDate endBeforePromotion = promotionStartDate.minusDays(1);
                        long daysBeforePromotion = Math.min(bookingDTO.getDay(), ChronoUnit.DAYS.between(checkInDate, endBeforePromotion.plusDays(1)));
                        total += optionalProperty.getOriginalPrice() * daysBeforePromotion;
                    }

                    // Days during the promotion period
                    LocalDate startDuringPromotion = checkInDate.isAfter(promotionStartDate) ? checkInDate : promotionStartDate;
                    LocalDate endDuringPromotion = checkOutDate.isBefore(promotionEndDate) ? checkOutDate : promotionEndDate;
                    if (!startDuringPromotion.isAfter(endDuringPromotion)) {
                        long daysDuringPromotion = ChronoUnit.DAYS.between(startDuringPromotion, endDuringPromotion.plusDays(1));
                        total += optionalProperty.getPromotionPrice() * daysDuringPromotion;
                    }

                    // Days after the promotion ends
                    if (checkOutDate.isAfter(promotionEndDate)) {
                        LocalDate startAfterPromotion = promotionEndDate.plusDays(1);
                        long daysAfterPromotion = ChronoUnit.DAYS.between(startAfterPromotion, checkOutDate.minusDays(0));
                        total += optionalProperty.getOriginalPrice() * daysAfterPromotion;
                    }

                    booking.setTotalPrice(total);
                }
            }


            if (bookingRepository.existsByPropertyIdAndOverlappingDates(optionalProperty.getPropertyId(), booking.getCheckInDate(),booking.getCheckOutDate())) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Booking could not be made because property with id: " + optionalProperty.getPropertyId() + " is occupied");
                errors.add(errorDTO);
                return null;
            } else {
                if (optinalUser.getCards().isEmpty()) {
                    ErrorDTO errorDTO = new ErrorDTO();
                    errorDTO.setErrors(true);
                    errorDTO.setMessage("Without a card you cannot create a booking");
                    errors.add(errorDTO);
                    responseBody.setError(errors);
                    return null;
                }

                Booking createdBooking = bookingRepository.save(booking);

                SuccessDTO successDTO = new SuccessDTO();
                successDTO.setSuccess(true);
                successDTO.setMessage("The booking was created successfully with ID: " + createdBooking.getBookingId());
                successes.add(successDTO);
                return createdBooking;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());



        optinalUser.setBookings(bookings);
        User savedUser = userRepository.save(optinalUser);

        optionalProperty.setBookings(bookings);
        Property savedProperty = propertyRepository.save(optionalProperty);

        bookings.forEach(booking -> booking.setUser(savedUser));
        bookings.forEach(booking -> booking.setProperty(savedProperty));
        bookingRepository.saveAll(bookings);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        modelMapper.map(savedUser, UserDTO.class);
        modelMapper.map(savedProperty, PropertyDTO.class);

        return responseBody;

    }

    @Override
    public Booking findBookingByUserId(Long userId, Long bookingId) {

        return bookingRepository.findBookingByUserId(userId, bookingId);

    }

    @Override
    public ResponseBody updateBookingByUserForProperty(Long userId, Long propertyId, Long bookingId, List<BookingDTO> bookingList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User with id: " + userId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        User optionalUser = existingUser.get();


        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if (existingProperty.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Property with id: " + propertyId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();

        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);

        if (existingBooking.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Booking with id: " + bookingId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Booking optionalBooking = existingBooking.get();


        List<Booking> bookings = bookingList.stream().map(bookingDTO -> {
            modelMapper.map(bookingDTO, Booking.class);

            if (optionalProperty.getMaxOccupancy() < bookingDTO.getPeopleNumber()) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("The number of people: " + bookingDTO.getPeopleNumber() + " is higher than maximum occupancy capacity: " + optionalProperty.getMaxOccupancy());
                errors.add(errorDTO);
                return null;
            }

            modelMapper.map(bookingDTO, optionalBooking);

            optionalBooking.setCheckInDate(bookingDTO.getCheckInDate());
            LocalDate checkIn = optionalBooking.getCheckInDate();
            LocalDate checkOut = checkIn.plusDays(bookingDTO.getDay());
            optionalBooking.setCheckOutDate(checkOut);


            if (optionalProperty.getPromotion() == null) {
                double propertyPrice = optionalProperty.getOriginalPrice() * bookingDTO.getDay();
                optionalBooking.setTotalPrice(propertyPrice);
            } else {
                LocalDate checkInDate = optionalBooking.getCheckInDate();
                LocalDate checkOutDate = optionalBooking.getCheckOutDate();
                LocalDate promotionStartDate = optionalProperty.getPromotion().getStartDate();
                LocalDate promotionEndDate = optionalProperty.getPromotion().getEndDate();

                if (checkOutDate.isBefore(promotionStartDate) || checkInDate.isAfter(promotionEndDate)) {
                    double propertyPrice = optionalProperty.getOriginalPrice() * bookingDTO.getDay();
                    optionalBooking.setTotalPrice(propertyPrice);
                } else {
                    double total = 0.0;

                    // Days before the promotion starts
                    if (checkInDate.isBefore(promotionStartDate)) {
                        LocalDate endBeforePromotion = promotionStartDate.minusDays(1);
                        long daysBeforePromotion = Math.min(bookingDTO.getDay(), ChronoUnit.DAYS.between(checkInDate, endBeforePromotion.plusDays(1)));
                        total += optionalProperty.getOriginalPrice() * daysBeforePromotion;
                    }

                    // Days during the promotion period
                    LocalDate startDuringPromotion = checkInDate.isAfter(promotionStartDate) ? checkInDate : promotionStartDate;
                    LocalDate endDuringPromotion = checkOutDate.isBefore(promotionEndDate) ? checkOutDate : promotionEndDate;
                    if (!startDuringPromotion.isAfter(endDuringPromotion)) {
                        long daysDuringPromotion = ChronoUnit.DAYS.between(startDuringPromotion, endDuringPromotion.plusDays(1));
                        total += optionalProperty.getPromotionPrice() * daysDuringPromotion;
                    }

                    // Days after the promotion ends
                    if (checkOutDate.isAfter(promotionEndDate)) {
                        LocalDate startAfterPromotion = promotionEndDate.plusDays(1);
                        long daysAfterPromotion = ChronoUnit.DAYS.between(startAfterPromotion, checkOutDate.minusDays(0));
                        total += optionalProperty.getOriginalPrice() * daysAfterPromotion;
                    }

                    optionalBooking.setTotalPrice(total);
                }
            }
            if (bookingRepository.existsByPropertyIdAndOverlappingDates(optionalProperty.getPropertyId(), optionalBooking.getCheckInDate(),optionalBooking.getCheckOutDate())) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Booking could not be made because property with id: " + optionalProperty.getPropertyId() + " is occupied");
                errors.add(errorDTO);
                return null;
            }

            Booking updatedBooking = bookingRepository.save(optionalBooking);
            SuccessDTO successDTO = new SuccessDTO();
            successDTO.setSuccess(true);
            successDTO.setMessage("Booking was updated successfully");
            successes.add(successDTO);
            responseBody.setSuccess(successes);

            return updatedBooking;

        }).filter(Objects::nonNull).collect(Collectors.toList());

        optionalUser.setBookings(bookings);
        User savedUser = userRepository.save(optionalUser);

        optionalProperty.setBookings(bookings);
        Property savedProperty = propertyRepository.save(optionalProperty);

        bookings.forEach(booking -> booking.setUser(savedUser));
        bookings.forEach(booking -> booking.setProperty(savedProperty));
        bookingRepository.saveAll(bookings);

        modelMapper.map(savedUser, UserDTO.class);
        modelMapper.map(savedProperty, PropertyDTO.class);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;

    }
}
