package com.rental_management.dto;

import com.rental_management.entities.Property;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PromotionDTO {

    private LocalDate startDate;
    private double discountOffer;
    private String offerDetails;
    private Long promotionDays;
}
