package com.rental_management.entities;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Promotion {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double discountAmount;
    private String offerDetails;
    //@OneToMany
    private List<Property> properties;
}
