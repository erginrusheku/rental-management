package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;
    private Date checkInDate;
    private Date checkOutDate;
    private double totalPrice;
    private Status status;
    @ManyToOne
    private User user;
    @ManyToOne
    private Property property;
}
