package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDTO {

    private String title;
    private String description;
    private String location;
    private double originalPrice;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int maxOccupancy;

}
