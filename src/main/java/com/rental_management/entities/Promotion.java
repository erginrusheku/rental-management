package com.rental_management.entities;

import java.util.Date;

public class Promotion {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double discountAmount;
    private String offerDetails;
    private Promotion promotion;
    private Property property;
}
