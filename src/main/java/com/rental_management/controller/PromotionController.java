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

    @PutMapping("/updatePromotion/{ownerId}/{propertyId}/{promotionId}")
    ResponseEntity<ResponseBody> updatePromotion(@PathVariable Long ownerId,@PathVariable Long propertyId,@PathVariable Long promotionId,@RequestBody PromotionDTO promotionDTO){
        ResponseBody updatedPromotion = promotionService.updatePromotionByOwnerForProperties(ownerId, propertyId, promotionId, promotionDTO);
        return new ResponseEntity<>(updatedPromotion, HttpStatus.OK);
    }

    @DeleteMapping("/deletePromotionId/{promotionId}")
    ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId){
        promotionService.deletePromotionById(promotionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createPromotion/{ownerId}/{propertyId}")
    ResponseEntity<ResponseBody> createPromotionByOwnerForProperties(@PathVariable Long ownerId, @PathVariable Long propertyId, @RequestBody PromotionDTO promotionDTO){
        ResponseBody createPromotion = promotionService.createPromotionByOwnerForProperties(ownerId,propertyId,promotionDTO);
        return new ResponseEntity<>(createPromotion,HttpStatus.CREATED);
    }

    @DeleteMapping("/deletePromotion")
    ResponseEntity<ResponseBody> deletePromotion(@RequestParam Long ownerId, @RequestParam Long propertyId, @RequestParam Long promotionId){
        ResponseBody deletePromotion = promotionService.deletePromotion(ownerId,propertyId,promotionId);
        return new ResponseEntity<>(deletePromotion, HttpStatus.OK);
    }
}
