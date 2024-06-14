package com.rental_management.entities;

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
    private double discountAmount;
    private String offerDetails;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Property> properties;
}
