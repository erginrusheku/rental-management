package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.dto.UserDTO;
import com.rental_management.entities.User;
import com.rental_management.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(users -> modelMapper.map(users, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseBody createUser(UserDTO userDTO) {

        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        if (userDTO.getUserName() == null || userDTO.getUserLastName() == null || userDTO.getEmail() == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User not created because username, lastname, or email is missing");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);


        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("User with id: " + savedUser.getId() + " created successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);


        modelMapper.map(savedUser, UserDTO.class);

        return responseBody;
    }


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
