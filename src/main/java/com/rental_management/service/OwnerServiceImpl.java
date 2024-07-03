package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.OwnerDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.entities.Owner;
import com.rental_management.repo.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnerServiceImpl implements OwnerService{


    private final OwnerRepository ownerRepository;

    private final ModelMapper modelMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, ModelMapper modelMapper) {

        this.ownerRepository = ownerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OwnerDTO getOwnerById(Long id) {
        return null;
    }

    @Override
    public List<OwnerDTO> getAllOwners() {
        List<Owner> ownerList = ownerRepository.findAll();
        return ownerList.stream().map(owners -> modelMapper.map(owners, OwnerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseBody createOwner(OwnerDTO ownerDTO) {
        ResponseBody responseBody = new ResponseBody();
        List<SuccessDTO> successes = new ArrayList<>();
        List<ErrorDTO> errors = new ArrayList<>();

        if(ownerDTO.getName() == null || ownerDTO.getPhoneNumber() < 0 ){
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner not created because the name or the phone number is missing");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        if (ownerRepository.existsByPhoneNumber(ownerDTO.getPhoneNumber())) {
            ErrorDTO error = new ErrorDTO();
            error.setErrors(true);
            error.setMessage("Owner not created because the phone number is already in use");
            errors.add(error);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner owner = modelMapper.map(ownerDTO, Owner.class);
        Owner savedOwner = ownerRepository.save(owner);

        SuccessDTO success = new SuccessDTO();
        success.setSuccess(true);
        success.setMessage("Owner with Id: " + savedOwner.getId() + " was created successfully");
        successes.add(success);
        responseBody.setSuccess(successes);

        modelMapper.map(savedOwner, OwnerDTO.class);


        return responseBody;
    }

    @Override
    public ResponseBody updateOwner(Long ownerId, OwnerDTO ownerDTO) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();
        Optional<Owner> existingOwner = ownerRepository.findById(ownerId);
        if(existingOwner.isEmpty()){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Owner with id: "+ ownerId + " not found");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        Owner optionalOwner = existingOwner.get();

        modelMapper.map(ownerDTO,optionalOwner);

        ownerRepository.save(optionalOwner);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Owner with id: "+ optionalOwner.getId()+" updated successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);

        return responseBody;
    }

    @Override
    public ResponseBody deleteOwnerById(Long id) {
        ResponseBody responseBody = new ResponseBody();
        List<SuccessDTO> successes = new ArrayList<>();


        ownerRepository.deleteById(id);
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("Owner was deleted successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);

        return responseBody;
    }
}


