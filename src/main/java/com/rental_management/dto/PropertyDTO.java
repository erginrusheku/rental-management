package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PropertyDTO {
    private String title;
    private String description;
    private String location;
    private double pricePerNight;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int maximumOccupancy;
}
