package com.rental_management.controller;

import com.rental_management.dto.OwnerProperty;
import com.rental_management.dto.PropertyDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.entities.Property;
import com.rental_management.repo.PropertyRepository;
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

    @PostMapping("/create")
    ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO property = propertyService.createProperty(propertyDTO);
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/propertyId/{propertyId}")
    ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long propertyId) {
        PropertyDTO propertyIds = propertyService.getPropertyById(propertyId);
        return new ResponseEntity<>(propertyIds, HttpStatus.OK);
    }

    @PutMapping("/updateProperty/{propertyId}")
    ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long propertyId, @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO propertyAndId = propertyService.updateProperty(propertyId, propertyDTO);
        return new ResponseEntity<>(propertyAndId, HttpStatus.OK);
    }

    @DeleteMapping("/deletePropertyId/{propertyId}")
    ResponseEntity<Void> deletePropertyById(@PathVariable Long propertyId) {
        propertyService.deletePropertyById(propertyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/createProperty/{ownerId}")
    ResponseEntity<ResponseBody> createPropertiesByOwner(@PathVariable Long ownerId, @RequestBody List<PropertyDTO> propertyList) {
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
}
