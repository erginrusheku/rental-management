package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Property {
    private Long propertyId;
    private String title;
    private String description;
    private String type;
    private String location;
    private double pricePerNight;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int maximumOccupancy;
    private List<String> amenities;
}
