package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.PromotionDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.entities.Owner;
import com.rental_management.entities.Promotion;
import com.rental_management.entities.Property;
import com.rental_management.repo.OwnerRepository;
import com.rental_management.repo.PromotionRepository;
import com.rental_management.repo.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService{

    private final PromotionRepository promotionRepository;
    private final PropertyRepository propertyRepository;
    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;

    public PromotionServiceImpl(PromotionRepository promotionRepository, PropertyRepository propertyRepository, OwnerRepository ownerRepository, ModelMapper modelMapper) {
        this.promotionRepository = promotionRepository;
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PromotionDTO getPromotionById(Long id) {
        return null;
    }

    @Override
    public List<PromotionDTO> getAllPromotions() {
        return null;
    }

    @Override
    public PromotionDTO createPromotion(PromotionDTO promotionDTO) {
        return null;
    }

    @Override
    public PromotionDTO updatePromotion(Long promotionId, PromotionDTO promotionDTO) {
        return null;
    }

    @Override
    public void deletePromotionById(Long id) {

    }

    @Override
    public ResponseBody createPromotionByOwnerForProperties(Long ownerId, Long propertyId, PromotionDTO promotionDTO) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if(existingOwner.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner not found with id: " + ownerId);
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner optionalOwner = existingOwner.get();


        Optional<Property> existingProperty = propertyRepository.findById(propertyId);

            if(existingProperty.isEmpty()){
                ErrorDTO error = new ErrorDTO();
                error.setErrors(true);
                error.setMessage("Property not found with id: " + propertyId);
                errors.add(error);
                responseBody.setError(errors);
                return responseBody;
            }

            Property optionalProperty = existingProperty.get();

        Promotion promotion = modelMapper.map(promotionDTO, Promotion.class);
        promotion.setOwner(optionalOwner);
        promotion.setProperty(optionalProperty);



        double discountOffer = (promotionDTO.getDiscountOffer() / 100) * optionalProperty.getPricePerNight();
        double propertyPricePerNight = optionalProperty.getPricePerNight();
        double totalAmountByPercentage = propertyPricePerNight - discountOffer;
        optionalProperty.setPricePerNight(totalAmountByPercentage);

        if(discountOffer < 0 || discountOffer >= 100){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Promotion discount amount is greater than the property amount");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Promotion savedPromotion = promotionRepository.save(promotion);
        modelMapper.map(savedPromotion, PromotionDTO.class);

        SuccessDTO success = new SuccessDTO();
        success.setSuccess(true);
        success.setMessage("Promotion created successfully");
        successes.add(success);
        responseBody.setSuccess(successes);

        return responseBody;
    }
}
