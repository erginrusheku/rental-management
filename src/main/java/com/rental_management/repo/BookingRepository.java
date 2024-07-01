package com.rental_management.repo;

import com.rental_management.entities.Booking;
import com.rental_management.entities.Property;
import com.rental_management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bookingId = :bookingId")
    Booking findBookingByUserId(@Param("userId") Long userId, @Param("bookingId") Long bookingId);

    boolean existsByPropertyAndCheckInDateBetween(Property property, Date checkInDate, Date checkOutDate);
   }
