package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Promotion {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double discountAmount;
    private String offerDetails;
    private Promotion promotion;
    private Property property;
}
