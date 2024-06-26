package com.rental_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private double discountOffer;
    private String offerDetails;

    @JsonIgnore
    @OneToOne
    private Property property;
    @JsonIgnore
    @ManyToOne
    private Owner owner;
}
