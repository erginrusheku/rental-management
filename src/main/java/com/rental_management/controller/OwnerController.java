package com.rental_management.controller;

import com.rental_management.dto.OwnerDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owner")
    ResponseEntity<OwnerDTO> getOwnerById(@RequestParam Long ownerId){
        OwnerDTO ownerIds = ownerService.getOwnerById(ownerId);
        return new ResponseEntity<>(ownerIds, HttpStatus.OK);
    }
    @GetMapping("/all")
    ResponseEntity<List<OwnerDTO>> getAllOwners(){
        List<OwnerDTO> owners = ownerService.getAllOwners();
        return new ResponseEntity<>(owners, HttpStatus.CREATED);
    }
    @PostMapping("/create")
    ResponseEntity<ResponseBody> createOwner(@RequestBody OwnerDTO ownerDTO){
        ResponseBody createdOwner = ownerService.createOwner(ownerDTO);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }
    @PutMapping("/updateOwnerId")
    ResponseEntity<ResponseBody> updateOwner(@RequestParam Long ownerId,@RequestBody OwnerDTO ownerDTO){
        ResponseBody updatedOwner = ownerService.updateOwner(ownerId, ownerDTO);
        return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
    }
    @DeleteMapping("/deleteOwner")
    ResponseEntity<ResponseBody> deleteOwner(@RequestParam Long ownerId){
        ResponseBody deleteOwner = ownerService.deleteOwnerById(ownerId);
        return new ResponseEntity<>(deleteOwner,HttpStatus.OK);
    }
}
