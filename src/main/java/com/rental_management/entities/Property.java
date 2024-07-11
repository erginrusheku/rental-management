package com.rental_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "property_id")
    private Long propertyId;
    private String title;
    private String description;
    private String location;
    private double originalPrice;
    private double promotionPrice;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int maxOccupancy;

    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Promotion promotion;

    @ManyToOne
    private Owner owner;
}
