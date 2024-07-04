package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.PromotionDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.entities.Card;
import com.rental_management.entities.Owner;
import com.rental_management.entities.Promotion;
import com.rental_management.entities.Property;
import com.rental_management.repo.OwnerRepository;
import com.rental_management.repo.PromotionRepository;
import com.rental_management.repo.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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
    public ResponseBody updatePromotionByOwnerForProperties(Long ownerId, Long propertyId, Long promotionId, PromotionDTO promotionDTO) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if(existingOwner.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner does not exist");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner optionalOwner = existingOwner.get();

        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if(existingProperty.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Property does not exist");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Property optionalProperty = existingProperty.get();

        Optional<Promotion> existingPromotion = promotionRepository.findById(promotionId);
        if(existingPromotion.isEmpty()){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Promotion does not exist");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Promotion optionalPromotion = existingPromotion.get();

        Promotion promotion = modelMapper.map(promotionDTO, Promotion.class);
        promotion.setProperty(optionalProperty);
        promotion.setOwner(optionalOwner);

        double discountOffer = (optionalProperty.getOriginalPrice() * promotionDTO.getDiscountOffer() / 100);
        double totalAmount = optionalProperty.getOriginalPrice() - discountOffer;
        optionalProperty.setPromotionPrice(totalAmount);

        if(discountOffer < 0 || discountOffer >= 100){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Promotion discount amount is greater than the property amount");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        optionalPromotion.setStartDate(promotionDTO.getStartDate());
        LocalDate startDate = optionalPromotion.getStartDate();
        LocalDate endDate = startDate.plusDays(promotionDTO.getPromotionDays());
        optionalPromotion.setEndDate(endDate);

        modelMapper.map(promotionDTO, optionalPromotion);

        promotionRepository.save(optionalPromotion);
        promotion.setOwner(optionalOwner);
        promotion.setProperty(optionalProperty);
        optionalOwner.getPromotions().add(promotion);
        optionalProperty.setPromotion(promotion);

        SuccessDTO success = new SuccessDTO();
        success.setSuccess(true);
        success.setMessage("Promotion updated successfully");
        successes.add(success);
        responseBody.setSuccess(successes);

        return responseBody;
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

        double discountOffer = (optionalProperty.getOriginalPrice() * promotionDTO.getDiscountOffer() / 100);
        double totalAmount = optionalProperty.getOriginalPrice() - discountOffer;
        optionalProperty.setPromotionPrice(totalAmount);

        if(discountOffer < 0 || discountOffer >= 100){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Promotion discount amount is greater than the property amount");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        promotion.setStartDate(promotionDTO.getStartDate());
        LocalDate startDate = promotion.getStartDate();
        LocalDate endDate = startDate.plusDays(promotionDTO.getPromotionDays());
        promotion.setEndDate(endDate);

        if(promotion.getStartDate().isAfter(promotion.getEndDate())){
            optionalProperty.setPromotionPrice(0);
        }

        Promotion savedPromotion = promotionRepository.save(promotion);
        savedPromotion.setOwner(optionalOwner);
        savedPromotion.setProperty(optionalProperty);
        optionalOwner.getPromotions().add(savedPromotion);
        optionalProperty.setPromotion(savedPromotion);

        modelMapper.map(savedPromotion, PromotionDTO.class);

        SuccessDTO success = new SuccessDTO();
        success.setSuccess(true);
        success.setMessage("Promotion created successfully");
        successes.add(success);
        responseBody.setSuccess(successes);

        return responseBody;
    }

    @Override
    public ResponseBody deletePromotion(Long ownerId, Long propertyId, Long promotionId) {
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

        Optional<Promotion> optionalPromotion = promotionRepository.findById(promotionId);
        if (optionalPromotion.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Promotion with id: "+ promotionId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }
        Promotion existingPromotion = optionalPromotion.get();

        optionalOwner.getPromotions().remove(existingPromotion);
        optionalProperty.setPromotion(null);

        promotionRepository.deleteById(existingPromotion.getId());

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Promotion was deleted successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);


        responseBody.setError(errors);
        responseBody.setSuccess(successes);

        return responseBody;
    }
}
