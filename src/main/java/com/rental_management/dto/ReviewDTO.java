package com.rental_management.dto;

import com.rental_management.entities.Rating;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Rating rating;
    private String comment;
    private String message;
}
