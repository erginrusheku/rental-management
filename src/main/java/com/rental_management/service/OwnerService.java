package com.rental_management.service;

import com.rental_management.dto.OwnerDTO;
import com.rental_management.dto.ResponseBody;

import java.util.List;

public interface OwnerService {

    OwnerDTO getOwnerById(Long id);

    List<OwnerDTO> getAllOwners();

    ResponseBody createOwner(OwnerDTO ownerDTO);

    ResponseBody updateOwner(Long ownerId, OwnerDTO ownerDTO);

    ResponseBody deleteOwnerById(Long id);
}
