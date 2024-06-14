package com.rental_management.service;

import com.rental_management.dto.PromotionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService{
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
}
