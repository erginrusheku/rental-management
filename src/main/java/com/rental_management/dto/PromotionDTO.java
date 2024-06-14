package com.rental_management.dto;

import com.rental_management.entities.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class PromotionDTO {
    private Date startDate;
    private Date endDate;
    private double discountAmount;
    private String offerDetails;
    private Property property;
}
