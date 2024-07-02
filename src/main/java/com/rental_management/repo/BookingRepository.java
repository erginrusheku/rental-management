package com.rental_management.repo;

import com.rental_management.entities.Booking;
import com.rental_management.entities.Property;
import com.rental_management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bookingId = :bookingId")
    Booking findBookingByUserId(@Param("userId") Long userId, @Param("bookingId") Long bookingId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.property.propertyId = :propertyId AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)")
    boolean existsByPropertyIdAndOverlappingDates(@Param("propertyId") Long propertyId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);
   }
