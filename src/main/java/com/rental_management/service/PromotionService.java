package com.rental_management.service;

import com.rental_management.dto.PromotionDTO;
import com.rental_management.dto.ResponseBody;

import java.util.List;

public interface PromotionService {
    PromotionDTO getPromotionById(Long id);
    List<PromotionDTO> getAllPromotions();
    PromotionDTO createPromotion(PromotionDTO promotionDTO);
    ResponseBody updatePromotionByOwnerForProperties(Long ownerId, Long propertyId, Long promotionId, PromotionDTO promotionDTO);
    void deletePromotionById(Long id);
    ResponseBody createPromotionByOwnerForProperties(Long ownerId, Long propertyId, PromotionDTO promotionDTO);
    ResponseBody deletePromotion(Long ownerId, Long PropertyId, Long promotionId);

}
