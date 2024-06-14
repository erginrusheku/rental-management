package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long settingsId;
    private int maxOccupancy;
    private int minStayDuration;
    private CancellationPolicy cancellationPolicy;
    @OneToOne
    private Property property;
}
