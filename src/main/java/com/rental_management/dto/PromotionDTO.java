package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class PromotionDTO {

    private LocalDate startDate;
    private double discountOffer;
    private String offerDetails;
    private Long promotionDays;
}
