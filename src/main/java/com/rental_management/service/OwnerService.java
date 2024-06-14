package com.rental_management.service;

import com.rental_management.dto.OwnerDTO;

import java.util.List;

public interface OwnerService {

    OwnerDTO getOwnerById(Long id);
    List<OwnerDTO> getAllOwners();
    OwnerDTO createOwner(OwnerDTO ownerDTO);
    OwnerDTO updateOwner(Long ownerId, OwnerDTO ownerDTO);
    void deleteOwnerById(Long id);
}
