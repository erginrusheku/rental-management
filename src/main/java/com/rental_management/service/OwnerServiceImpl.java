package com.rental_management.service;

import com.rental_management.dto.OwnerDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OwnerServiceImpl implements OwnerService{
    @Override
    public OwnerDTO getOwnerById(Long id) {
        return null;
    }

    @Override
    public List<OwnerDTO> getAllOwners() {
        return null;
    }

    @Override
    public OwnerDTO createOwner(OwnerDTO ownerDTO) {
        return null;
    }

    @Override
    public OwnerDTO updateOwner(Long ownerId, OwnerDTO ownerDTO) {
        return null;
    }

    @Override
    public void deleteOwnerById(Long id) {

    }
}
