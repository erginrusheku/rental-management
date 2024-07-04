package com.rental_management.service;

import com.rental_management.dto.PropertyDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.Property;

import java.util.List;

public interface PropertyService {
    PropertyDTO getPropertyById(Long id);
    List<PropertyDTO> getAllProperties();
    ResponseBody createPropertiesByOwner(Long ownerId, List<PropertyDTO> propertyList);
    Property findPropertyByOwnerId(Long ownerId, Long propertyId);
    Property findPromotionByPropertyId(Long propertyId, Long promotionId);
    ResponseBody updatePropertyByOwner(Long ownerId, Long propertyId, List<PropertyDTO> propertyList);
    ResponseBody deleteProperty(Long ownerId, Long propertyId);

}
