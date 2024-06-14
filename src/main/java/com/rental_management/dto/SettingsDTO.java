package com.rental_management.dto;

import com.rental_management.entities.CancellationPolicy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingsDTO {
    private Long propertyId;
    private int maxOccupancy;
    private int minStayDuration;
    private CancellationPolicy cancellationPolicy;
}
