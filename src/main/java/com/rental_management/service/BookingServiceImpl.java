package com.rental_management.service;

import com.rental_management.dto.BookingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{
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
}
