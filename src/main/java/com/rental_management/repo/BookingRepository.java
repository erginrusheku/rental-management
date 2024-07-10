package com.rental_management.repo;

import com.rental_management.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.bookingId = :bookingId")
    Booking findBookingByUserId(@Param("userId") Long userId, @Param("bookingId") Long bookingId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.property.propertyId = :propertyId AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)")
    boolean existsByPropertyIdAndOverlappingDates(@Param("propertyId") Long propertyId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);

    void deleteMessageByUserId(Long userId);

}
