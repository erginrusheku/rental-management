package com.rental_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int day;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int peopleNumber;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Property property;
}
