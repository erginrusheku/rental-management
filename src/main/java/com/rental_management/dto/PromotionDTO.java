package com.rental_management.dto;

import com.rental_management.entities.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PromotionDTO {

    private double discountOffer;
    private String offerDetails;
    private Long promotionDays;
}
