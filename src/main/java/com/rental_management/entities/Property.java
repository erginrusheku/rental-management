package com.rental_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "property_id")
    private Long propertyId;
    private String title;
    private String description;
    private String location;
    private double pricePerNight;
    private double promotionPrice;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int maxOccupancy;

    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Promotion promotion;
    @JsonIgnore
    @ManyToOne
    private Owner owner;
    @JsonIgnore
    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Booking> bookings;
    @JsonIgnore
    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Review> reviews;
}
