package com.rental_management.service;

import com.rental_management.dto.PropertyDTO;

import java.util.List;

public interface PropertyService {
    PropertyDTO getPropertyById(Long id);
    List<PropertyDTO> getAllProperties();
    PropertyDTO createProperty(PropertyDTO propertyDTO);
    PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);
    void deletePropertyById(Long id);

}
