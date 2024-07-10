package com.rental_management.controller;

import com.rental_management.dto.PropertyDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.Property;
import com.rental_management.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/all")
    ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> propertyList = propertyService.getAllProperties();
        return new ResponseEntity<>(propertyList, HttpStatus.OK);
    }

    @GetMapping("/propertyId")
    ResponseEntity<PropertyDTO> getPropertyById(@RequestParam Long propertyId) {
        PropertyDTO propertyIds = propertyService.getPropertyById(propertyId);
        return new ResponseEntity<>(propertyIds, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProperty")
    public ResponseEntity<ResponseBody> deleteProperty(@RequestParam Long propertyId) {
       ResponseBody deleteProperty = propertyService.deleteProperty(propertyId);
       return new ResponseEntity<>(deleteProperty, HttpStatus.OK);
    }

    @PostMapping("/createProperty")
    ResponseEntity<ResponseBody> createPropertiesByOwner(@RequestParam Long ownerId, @RequestBody List<PropertyDTO> propertyList) {
        ResponseBody createPropertyByOwner = propertyService.createPropertiesByOwner(ownerId, propertyList);
        return new ResponseEntity<>(createPropertyByOwner, HttpStatus.CREATED);
    }

    @GetMapping("/{ownerId}/{propertyId}")
    ResponseEntity<Property> findPropertyByOwnerId(@PathVariable Long ownerId,@PathVariable Long propertyId){
        Property getPropertyFromOwnerId = propertyService.findPropertyByOwnerId(ownerId, propertyId);
        return new ResponseEntity<>(getPropertyFromOwnerId, HttpStatus.OK);
    }

    @GetMapping("/propertyPromotion/{propertyId}/{promotionId}")
    ResponseEntity<Property> findPropertyByPromotionId(@PathVariable Long propertyId,@PathVariable Long promotionId){
        Property getPropertyFromPromotionId = propertyService.findPromotionByPropertyId(propertyId, promotionId);
        return new ResponseEntity<>(getPropertyFromPromotionId, HttpStatus.OK);
    }

    @PutMapping("/updateProperty")
    public ResponseEntity<ResponseBody> updatePropertyByOwner(@RequestParam Long ownerId, @RequestParam Long propertyId, @RequestBody List<PropertyDTO> propertyList){
        ResponseBody updateProperty = propertyService.updatePropertyByOwner(ownerId,propertyId,propertyList);
        return new ResponseEntity<>(updateProperty, HttpStatus.OK);
    }
}
