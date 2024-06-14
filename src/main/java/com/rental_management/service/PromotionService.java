package com.rental_management.service;

import com.rental_management.dto.PromotionDTO;

import java.util.List;

public interface PromotionService {
    PromotionDTO getPromotionById(Long id);
    List<PromotionDTO> getAllPromotions();
    PromotionDTO createPromotion(PromotionDTO promotionDTO);
    PromotionDTO updatePromotion(Long promotionId, PromotionDTO promotionDTO);
    void deletePromotionById(Long id);
}
