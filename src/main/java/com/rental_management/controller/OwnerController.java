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
@CrossOrigin(origins = "http://localhost:4200")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/getOwner/{ownerId}")
    ResponseEntity<OwnerDTO> getOwnerById(@PathVariable Long ownerId) {
        OwnerDTO ownerIds = ownerService.getOwnerById(ownerId);
        return new ResponseEntity<>(ownerIds, HttpStatus.OK);
    }

    @GetMapping("/allOwners")
    ResponseEntity<List<OwnerDTO>> getAllOwners() {
        List<OwnerDTO> owners = ownerService.getAllOwners();
        return new ResponseEntity<>(owners, HttpStatus.CREATED);
    }

    @PostMapping("/createOwner")
    ResponseEntity<ResponseBody> createOwner(@RequestBody OwnerDTO ownerDTO) {
        ResponseBody createdOwner = ownerService.createOwner(ownerDTO);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @PutMapping("/updateOwner/{ownerId}")
    ResponseEntity<ResponseBody> updateOwner(@PathVariable Long ownerId, @RequestBody OwnerDTO ownerDTO) {
        ResponseBody updatedOwner = ownerService.updateOwner(ownerId, ownerDTO);
        return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
    }

    @DeleteMapping("/deleteOwner/{ownerId}")
    ResponseEntity<ResponseBody> deleteOwner(@PathVariable Long ownerId) {
        ResponseBody deleteOwner = ownerService.deleteOwnerById(ownerId);
        return new ResponseEntity<>(deleteOwner, HttpStatus.OK);
    }
}
