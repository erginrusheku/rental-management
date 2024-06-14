package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {

    private Long settingsId;
    private int maxOccupancy;
    private int minStayDuration;
    private Property property;
    private CancellationPolicy cancellationPolicy;

}
