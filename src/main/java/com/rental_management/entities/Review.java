package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Review {
    private Long reviewId;
    private Rating rating;
    private String comment;
    private Date date;
    private String message;
    private User user;
    private Property property;
}
