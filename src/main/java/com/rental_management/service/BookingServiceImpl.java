package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Booking;
import com.rental_management.entities.Card;
import com.rental_management.entities.Property;
import com.rental_management.entities.User;
import com.rental_management.repo.BookingRepository;
import com.rental_management.repo.CardRepository;
import com.rental_management.repo.PropertyRepository;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService{

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl(UserRepository userRepository, PropertyRepository propertyRepository, CardRepository cardRepository, ModelMapper modelMapper, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.cardRepository = cardRepository;
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
        if(existingUser.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("User not found: "+ userId);
            errors.add(error);
            //responseBody.setError(errors);
            return responseBody;
        }

        User optinalUser = existingUser.get();

        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if(existingProperty.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property not found: "+ propertyId);
            errors.add(error);
            //responseBody.setError(errors);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();


        List<Booking> bookings = bookingList.stream().map(bookingDTO -> {
            Booking booking = modelMapper.map(bookingDTO, Booking.class);

            Booking createdBooking = bookingRepository.save(booking);

            double propertyPrice = optionalProperty.getPricePerNight();
            booking.setTotalPrice(propertyPrice);

            if (createdBooking.getBookingId() == null) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Booking could not be finalized without a booking ID");
                errors.add(errorDTO);
                return null;
            } else {
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

        return  responseBody;

        //The Card to be connected in the FE
    }
}
