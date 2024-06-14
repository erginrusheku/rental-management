package com.rental_management.service;

import com.rental_management.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    List<BookingDTO> getAllBookings();
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO getById(Long bookingId);
    BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO);
    void deleteById(Long bookingId);
}
