package com.rental_management.service;

import com.rental_management.dto.PropertyDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.Property;

import java.util.List;

public interface PropertyService {
    PropertyDTO getPropertyById(Long id);
    List<PropertyDTO> getAllProperties();
    PropertyDTO createProperty(PropertyDTO propertyDTO);
    PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);
    void deletePropertyById(Long id);
    ResponseBody createPropertiesByOwner(Long ownerId, List<PropertyDTO> propertyList);
    Property findPropertyByOwnerId(Long ownerId, Long propertyId);
    Property findPromotionByPropertyId(Long propertyId, Long promotionId);
    ResponseBody updatePropertyByOwner(Long ownerId, Long propertyId, List<PropertyDTO> propertyList);
    void deleteProperty(Long ownerId, Long propertyId);

}
