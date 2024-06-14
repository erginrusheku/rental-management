package com.rental_management.controller;

import com.rental_management.dto.PromotionDTO;
import com.rental_management.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/promotionId/{promotionId}")
    ResponseEntity<PromotionDTO> getPromotionById(@PathVariable Long promotionId){
        PromotionDTO promotionIds = promotionService.getPromotionById(promotionId);
        return new ResponseEntity<>(promotionIds, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<PromotionDTO>> getAllPromotions(){
        List<PromotionDTO> promotions = promotionService.getAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<PromotionDTO> createPromotion(@RequestBody PromotionDTO promotionDTO){
        PromotionDTO createdPromotions = promotionService.createPromotion(promotionDTO);
        return new ResponseEntity<>(createdPromotions, HttpStatus.CREATED);
    }

    @PutMapping("/updatePromotion/{promotionId}")
    ResponseEntity<PromotionDTO> updatePromotion(@PathVariable Long promotionId,@RequestBody PromotionDTO promotionDTO){
        PromotionDTO updatedPromotion = promotionService.updatePromotion(promotionId, promotionDTO);
        return new ResponseEntity<>(updatedPromotion, HttpStatus.OK);
    }

    @DeleteMapping("/deletePromotionId/{promotionId}")
    ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId){
        promotionService.deletePromotionById(promotionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
