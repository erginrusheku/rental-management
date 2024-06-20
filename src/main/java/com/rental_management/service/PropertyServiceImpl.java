package com.rental_management.service;

import com.rental_management.dto.PropertyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService{
    @Override
    public PropertyDTO getPropertyById(Long id) {
        return null;
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        return null;
    }

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {

        return null;
    }

    @Override
    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        return null;
    }

    @Override
    public void deletePropertyById(Long id) {

    }
}
