package com.rental_management.service;

import com.rental_management.dto.BookingDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.Booking;

import java.util.List;

public interface BookingService {
    List<BookingDTO> getAllBookings();
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getById(Long bookingId);
    ResponseBody createBookingByUserForProperty(Long userId, Long propertyId, List<BookingDTO> bookingList);
    Booking findBookingByUserId(Long userId, Long bookingId);
    ResponseBody updateBookingByUserForProperty(Long userId, Long propertyId, Long bookingId, List<BookingDTO> bookingList);
    ResponseBody deleteBookings(/*Long userId, Long propertyId,*/ Long bookingId);
}
