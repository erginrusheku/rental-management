package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long propertyId;
    private String title;
    private String description;
    private String type;
    private String location;
    private double pricePerNight;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int maximumOccupancy;

    @ManyToOne
    private Promotion promotion;
    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Booking> bookings;
    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Review> reviews;
    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Settings settings;
}
