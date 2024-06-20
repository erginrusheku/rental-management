package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Owner;
import com.rental_management.entities.Property;
import com.rental_management.repo.OwnerRepository;
import com.rental_management.repo.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService{

    private final OwnerRepository ownerRepository;
    private final PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public PropertyServiceImpl(OwnerRepository ownerRepository, PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.ownerRepository = ownerRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

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

    @Override
    public ResponseBody getPropertiesByOwner(Long ownerId, OwnerProperty ownerProperty) {
        ResponseBody responseBody = new ResponseBody();
        List<SuccessDTO> successes = new ArrayList<>();
        List<ErrorDTO> errors = new ArrayList<>();

        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if(existingOwner.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner with Id: " + ownerId + "not found!");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        List<Property> propertiesList = new ArrayList<>();
        propertiesList.forEach(property -> {
            Optional<Property> existingProperty = propertyRepository.findById(property.getPropertyId());
        if(existingProperty.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property with Id: " + property.getPropertyId() + " not found!");
            errors.add(error);
            responseBody.setError(errors);
        }else{
            propertiesList.add(existingProperty.get());
        }


        }
                );

        Owner owner = new Owner();
        owner.setProperties(new ArrayList<>(propertiesList));
        Owner savedOwner = ownerRepository.save(owner);

        propertiesList.forEach(property -> property.setOwner(savedOwner));
        propertyRepository.saveAll(propertiesList);
        responseBody.setError(errors);
        responseBody.setSuccess(successes);
        modelMapper.map(savedOwner, OwnerDTO.class);
        return responseBody;
    }
}
