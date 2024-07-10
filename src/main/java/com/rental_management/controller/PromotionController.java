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

    @GetMapping("/promotionId")
    ResponseEntity<PromotionDTO> getPromotionById(@RequestParam Long promotionId){
        PromotionDTO promotionIds = promotionService.getPromotionById(promotionId);
        return new ResponseEntity<>(promotionIds, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<PromotionDTO>> getAllPromotions(){
        List<PromotionDTO> promotions = promotionService.getAllPromotions();
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

    @PutMapping("/updatePromotion")
    ResponseEntity<ResponseBody> updatePromotion(@RequestParam Long ownerId,@RequestParam Long propertyId,@RequestParam Long promotionId,@RequestBody PromotionDTO promotionDTO){
        ResponseBody updatedPromotion = promotionService.updatePromotionByOwnerForProperties(ownerId, propertyId, promotionId, promotionDTO);
        return new ResponseEntity<>(updatedPromotion, HttpStatus.OK);
    }

    @PostMapping("/createPromotion")
    ResponseEntity<ResponseBody> createPromotionByOwnerForProperties(@RequestParam Long ownerId, @RequestParam Long propertyId, @RequestBody PromotionDTO promotionDTO){
        ResponseBody createPromotion = promotionService.createPromotionByOwnerForProperties(ownerId,propertyId,promotionDTO);
        return new ResponseEntity<>(createPromotion,HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePromotion")
    ResponseEntity<ResponseBody> deletePromotion(@RequestParam Long propertyId, @RequestParam Long promotionId){
        ResponseBody deletePromotion = promotionService.deletePromotion(propertyId,promotionId);
        return new ResponseEntity<>(deletePromotion, HttpStatus.OK);
    }
}
