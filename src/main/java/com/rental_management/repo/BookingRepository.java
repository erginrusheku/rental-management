package com.rental_management.repo;

import com.rental_management.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bookingId = :bookingId")
    Booking findBookingByUserId(@Param("userId") Long userId, @Param("bookingId") Long bookingId);
}
