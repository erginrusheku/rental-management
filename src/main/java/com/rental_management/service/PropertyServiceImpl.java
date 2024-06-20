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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

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
    public ResponseBody createPropertiesByOwner(Long ownerId, List<PropertyDTO> propertyList) {
        ResponseBody responseBody = new ResponseBody();
        List<SuccessDTO> successes = new ArrayList<>();
        List<ErrorDTO> errors = new ArrayList<>();

        Optional<Owner> existingOwnerOptional = ownerRepository.findById(ownerId);
        if (existingOwnerOptional.isEmpty()) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner with Id: " + ownerId + " not found!");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner existingOwner = existingOwnerOptional.get();

        List<Property> propertiesList = propertyList.stream()
                .map(propertyDto -> {
                    Property property = modelMapper.map(propertyDto, Property.class);
                    Property createdProperty = propertyRepository.save(property);

                    if (createdProperty.getPropertyId() == null) {
                        ErrorDTO errorDTO = new ErrorDTO();
                        errorDTO.setErrors(true);
                        errorDTO.setMessage("Property not created without id");
                        errors.add(errorDTO);
                        return null;
                    } else {
                        SuccessDTO success = new SuccessDTO();
                        success.setSuccess(true);
                        success.setMessage("Property with Id: " + createdProperty.getPropertyId() + " successfully created!");
                        successes.add(success);
                        return createdProperty;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        existingOwner.setProperties(propertiesList);
        Owner savedOwner = ownerRepository.save(existingOwner);

        propertiesList.forEach(property -> property.setOwner(savedOwner));
        propertyRepository.saveAll(propertiesList);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        modelMapper.map(savedOwner, OwnerDTO.class);

        return responseBody;
    }


}
