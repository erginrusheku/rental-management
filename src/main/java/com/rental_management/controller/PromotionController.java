package com.rental_management.controller;

import com.rental_management.dto.PromotionDTO;
import com.rental_management.dto.ResponseBody;
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

    @GetMapping("/getPromotion/{promotionId}")
    ResponseEntity<PromotionDTO> getPromotionById(@PathVariable Long promotionId) {
        PromotionDTO promotionIds = promotionService.getPromotionById(promotionId);
        return new ResponseEntity<>(promotionIds, HttpStatus.OK);
    }

    @GetMapping("/allPromotions")
    ResponseEntity<List<PromotionDTO>> getAllPromotions() {
        List<PromotionDTO> promotions = promotionService.getAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @PutMapping("/updatePromotion/{ownerId}/{propertyId}/{promotionId}")
    ResponseEntity<ResponseBody> updatePromotion(@PathVariable Long ownerId, @PathVariable Long propertyId, @PathVariable Long promotionId, @RequestBody PromotionDTO promotionDTO) {
        ResponseBody updatedPromotion = promotionService.updatePromotionByOwnerForProperties(ownerId, propertyId, promotionId, promotionDTO);
        return new ResponseEntity<>(updatedPromotion, HttpStatus.OK);
    }

    @PostMapping("/createPromotion/{ownerId}/{propertyId}")
    ResponseEntity<ResponseBody> createPromotionByOwnerForProperties(@PathVariable Long ownerId, @PathVariable Long propertyId, @RequestBody PromotionDTO promotionDTO) {
        ResponseBody createPromotion = promotionService.createPromotionByOwnerForProperties(ownerId, propertyId, promotionDTO);
        return new ResponseEntity<>(createPromotion, HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePromotion/{propertyId}/{promotionId}")
    ResponseEntity<ResponseBody> deletePromotion(@PathVariable Long propertyId, @PathVariable Long promotionId) {
        ResponseBody deletePromotion = promotionService.deletePromotion(propertyId, promotionId);
        return new ResponseEntity<>(deletePromotion, HttpStatus.OK);
    }
}
