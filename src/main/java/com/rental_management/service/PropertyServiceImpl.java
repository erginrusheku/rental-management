package com.rental_management.service;

import com.rental_management.dto.*;
import com.rental_management.entities.Owner;
import com.rental_management.entities.Property;
import com.rental_management.repo.OwnerRepository;
import com.rental_management.repo.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Property> propertyList = propertyRepository.findAll();
        return propertyList.stream().map(property -> modelMapper.map(property, PropertyDTO.class)).toList();
    }

    @Override
    @Transactional
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

        List<Property> propertiesList = propertyList.stream().map(propertyDto -> {
            if (propertyDto.getTitle() == null) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Property not created without title");
                errors.add(errorDTO);
                return null;
            } else {
                Property property = modelMapper.map(propertyDto, Property.class);
                property.setOwner(existingOwner);
                Property createdProperty = propertyRepository.save(property);
                SuccessDTO success = new SuccessDTO();
                success.setSuccess(true);
                success.setMessage("Property with Id: " + createdProperty.getPropertyId() + " successfully created!");
                successes.add(success);
                return createdProperty;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        ownerRepository.save(existingOwner);

        propertyRepository.saveAll(propertiesList);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);


        return responseBody;
    }


    @Override
    public Property findPropertyByOwnerId(Long ownerId, Long propertyId) {
        return propertyRepository.findPropertyByOwnerId(ownerId, propertyId);
    }

    @Override
    public Property findPromotionByPropertyId(Long propertyId, Long promotionId) {
        return propertyRepository.findPromotionByPropertyId(propertyId, promotionId);
    }

    @Override
    public ResponseBody updatePropertyByOwner(Long ownerId, Long propertyId, List<PropertyDTO> propertyList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if (existingOwner.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Owner with id: " + ownerId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner optionalOwner = existingOwner.get();

        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if (existingProperty.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Property with id: " + propertyId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();

        List<Property> properties = propertyList.stream().map(propertyDTO -> {
            if (optionalProperty.getTitle() == null) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Property without title cannot created");
                errors.add(errorDTO);
                responseBody.setError(errors);
                return null;
            } else {
                modelMapper.map(propertyDTO, optionalProperty);

                if (optionalProperty.getPromotion() == null) {

                    optionalProperty.setOriginalPrice(propertyDTO.getOriginalPrice());
                } else {

                    double discount = (propertyDTO.getOriginalPrice() * optionalProperty.getPromotion().getDiscountOffer()) / 100;
                    double price = propertyDTO.getOriginalPrice() - discount;
                    optionalProperty.setPromotionPrice(price);
                }

                Property createProperty = propertyRepository.save(optionalProperty);

                createProperty.setOwner(optionalOwner);

                SuccessDTO successDTO = new SuccessDTO();
                successDTO.setSuccess(true);
                successDTO.setMessage("Property was updated successfully");
                successes.add(successDTO);
                responseBody.setSuccess(successes);
                return createProperty;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        ownerRepository.save(optionalOwner);

        propertyRepository.saveAll(properties);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;
    }

    @Override
    @Transactional
    public ResponseBody deleteProperty(Long propertyId) {

        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Property with id: " + propertyId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Property existingProperty = optionalProperty.get();

        propertyRepository.delete(existingProperty);
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Property was deleted successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;
    }

}
