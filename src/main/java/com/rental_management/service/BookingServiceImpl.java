package com.rental_management.service;

import com.rental_management.controller.BookingController;
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

                booking.setCheckInDate(Date.from(Instant.now()));
                LocalDate checkIn = booking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate checkOut = checkIn.plusDays(bookingDTO.getDay());
                Instant instant = checkOut.atStartOfDay(ZoneId.systemDefault()).toInstant();
                booking.setCheckOutDate(Date.from(instant));

            if(optionalProperty.getPromotion() == null){

                double propertyPrice = optionalProperty.getOriginalPrice() * bookingDTO.getDay();
                booking.setTotalPrice(propertyPrice);}
            else if(booking.getCheckOutDate().after(optionalProperty.getPromotion().getEndDate())){
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("The Promotion day "+optionalProperty.getPromotion().getStartDate()+" until "
                        + optionalProperty.getPromotion().getEndDate()+" is no longer available for this booking day: "+ booking.getCheckOutDate());
                errors.add(errorDTO);
                responseBody.setError(errors);
                return null;
            } else {
                double propertyPrice1 = optionalProperty.getPromotionPrice() * bookingDTO.getDay();
                booking.setTotalPrice(propertyPrice1);
            }


            if (bookingRepository.existsByPropertyAndCheckInDateBetween(optionalProperty,bookingDTO.getCheckInDate(),bookingDTO.getCheckOutDate())) {
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

            optionalBooking.setCheckInDate(Date.from(Instant.now()));
            LocalDate checkIn = optionalBooking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate checkOut = checkIn.plusDays(bookingDTO.getDay());
            Instant instant = checkOut.atStartOfDay(ZoneId.systemDefault()).toInstant();
            optionalBooking.setCheckOutDate(Date.from(instant));

            if(optionalProperty.getPromotion() == null){

                double propertyPrice = optionalProperty.getOriginalPrice() * bookingDTO.getDay();
                optionalBooking.setTotalPrice(propertyPrice);}
            else if(optionalBooking.getCheckOutDate().after(optionalProperty.getPromotion().getEndDate())){
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("The Promotion day "+optionalProperty.getPromotion().getStartDate()+" until "
                        + optionalProperty.getPromotion().getEndDate()+" is no longer available for this booking day: "+ optionalBooking.getCheckOutDate());
                errors.add(errorDTO);
                responseBody.setError(errors);
                return null;
            } else {
                double propertyPrice1 = optionalProperty.getPromotionPrice() * bookingDTO.getDay();
                optionalBooking.setTotalPrice(propertyPrice1);
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
