package com.rental_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;
    @Enumerated(EnumType.STRING)
    private Rating rating;
    private String comment;
    private Date date;
    private String message;
    @JsonIgnore
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne
    private Property property;
}
