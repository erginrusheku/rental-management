package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Booking {
    private Long bookingId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private Status status;
    private User user;
    private Property property;
}
