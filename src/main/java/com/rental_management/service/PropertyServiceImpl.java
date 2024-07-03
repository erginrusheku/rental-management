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

        List<Property> propertiesList = propertyList.stream()
                .map(propertyDto -> {
                    if (propertyDto.getTitle() == null) {
                        ErrorDTO errorDTO = new ErrorDTO();
                        errorDTO.setErrors(true);
                        errorDTO.setMessage("Property not created without title");
                        errors.add(errorDTO);
                        return null;
                    } else {
                        Property property = modelMapper.map(propertyDto, Property.class);
                        property.setOwner(existingOwner);
                        existingOwner.getProperties().add(property); //
                        Property createdProperty = propertyRepository.save(property);
                        SuccessDTO success = new SuccessDTO();
                        success.setSuccess(true);
                        success.setMessage("Property with Id: " + createdProperty.getPropertyId() + " successfully created!");
                        successes.add(success);
                        return createdProperty;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

       /* existingOwner.setProperties(propertiesList);
        Owner savedOwner = ownerRepository.save(existingOwner);



        propertiesList.forEach(property -> property.setOwner(savedOwner));
        propertyRepository.saveAll(propertiesList);

*/
        //existingOwner.setProperties(propertiesList); // Set properties on owner
        ownerRepository.save(existingOwner);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        //modelMapper.map(savedOwner, OwnerDTO.class);

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
        if(existingOwner.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Owner with id: "+ ownerId +" not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner optionalOwner = existingOwner.get();

        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if(existingProperty.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Property with id: "+ propertyId +" not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();

        List<Property> properties = propertyList.stream().map(propertyDTO -> {
            if(optionalProperty.getTitle() == null){
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrors(true);
                errorDTO.setMessage("Property without title cannot created");
                errors.add(errorDTO);
                responseBody.setError(errors);
                return null;
            } else {
                modelMapper.map(propertyDTO, optionalProperty);

                if(optionalProperty.getPromotion() == null){

                 optionalProperty.setOriginalPrice(propertyDTO.getOriginalPrice());
             }else {

             double discount = (propertyDTO.getOriginalPrice() * optionalProperty.getPromotion().getDiscountOffer()) / 100;
             double price = propertyDTO.getOriginalPrice() - discount;
             optionalProperty.setPromotionPrice(price);
             }

             Property createProperty = propertyRepository.save(optionalProperty);
                createProperty.setOwner(optionalOwner);
               optionalOwner.getProperties().add(createProperty);
             SuccessDTO successDTO = new SuccessDTO();
             successDTO.setSuccess(true);
             successDTO.setMessage("Property was updated successfully");
             successes.add(successDTO);
             responseBody.setSuccess(successes);
             return createProperty;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        //optionalOwner.setProperties(properties);
        ownerRepository.save(optionalOwner);

        //properties.forEach(property -> property.setOwner(savedOwner));
        propertyRepository.saveAll(properties);

        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;
    }

    @Override
    @Transactional
    public void deleteProperty(Long ownerId, Long propertyId) {
        // Find the owner
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        if (ownerOptional.isEmpty()) {
            throw new EntityNotFoundException("Owner with id " + ownerId + " not found");
        }
        Owner owner = ownerOptional.get();

        // Find the property by id
        Property property = owner.getProperties().stream()
                .filter(p -> p.getPropertyId().equals(propertyId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Property with id " + propertyId + " not found"));


        // Remove property from owner's collection
        owner.getProperties().remove(property);
        property.setOwner(null); // Optional: Clear the owner reference from the property

        // Delete the property
        propertyRepository.delete(property);
    }

}
