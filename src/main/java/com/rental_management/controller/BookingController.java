package com.rental_management.controller;

import com.rental_management.dto.BookingDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.Booking;
import com.rental_management.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @GetMapping("/all")
    ResponseEntity <List<BookingDTO>> getAllBookings(){
        List<BookingDTO> bookingList = bookingService.getAllBookings();
        return new ResponseEntity<>(bookingList,HttpStatus.OK);
    }
   @PostMapping("/create")
   ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO){
        BookingDTO booking =  bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
   }
   @GetMapping("/bookingId/{bookingId}")
   ResponseEntity<BookingDTO> getById(@PathVariable Long bookingId){
        BookingDTO bookingIds = bookingService.getById(bookingId);
        return new ResponseEntity<>(bookingIds, HttpStatus.OK);
   }



   @PostMapping("/createBooking")
    public ResponseEntity<ResponseBody> createBookingByUserForProperty(@RequestParam Long userId,@RequestParam Long propertyId,@RequestBody List<BookingDTO> bookingList){
        ResponseBody createBooking = bookingService.createBookingByUserForProperty(userId, propertyId, bookingList);
        return new ResponseEntity<>(createBooking, HttpStatus.CREATED);
   }

   @GetMapping("/{userId}/{bookingId}")
    ResponseEntity<Booking> findBookingByUserId(@PathVariable Long userId, @PathVariable Long bookingId){
        Booking getBookingByUserId = bookingService.findBookingByUserId(userId, bookingId);
        return new ResponseEntity<>(getBookingByUserId, HttpStatus.OK);
   }

    @PutMapping("/updateBookings")
    ResponseEntity<ResponseBody> updateBookingByUserForProperty(@RequestParam Long userId,@RequestParam Long propertyId,@RequestParam Long bookingId, @RequestBody List<BookingDTO> bookingDTO){
        ResponseBody bookingAndId = bookingService.updateBookingByUserForProperty(userId, propertyId, bookingId, bookingDTO);
        return new ResponseEntity<>(bookingAndId,HttpStatus.OK);
    }

    @DeleteMapping("/deleteBooking")
    public ResponseEntity<ResponseBody> deleteBookings(@RequestParam Long userId,@RequestParam Long propertyId,@RequestParam Long bookingId){
        ResponseBody deleteBooking = bookingService.deleteBookings(userId, propertyId, bookingId);
        return new ResponseEntity<>(deleteBooking, HttpStatus.OK);
    }
}
