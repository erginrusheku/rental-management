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

        if (userDTO.getUserName() == null || userDTO.getUserLastName() == null || userDTO.getEmail() == null || userDTO.getPersonalNumber() == null) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User not created because username, lastname, or email is missing");
            errors.add(errorDTO);
            responseBody.setError(errors);
        }



        User user = modelMapper.map(userDTO, User.class);

        if(userRepository.existsByPersonalNumber(user.getPersonalNumber())){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User with the same personal number cannot be created");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }


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
    public ResponseBody updateUser(Long userId, UserDTO userDTO) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("User not found with id: " + userId);
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        if(!userRepository.existsByPersonalNumber(userDTO.getPersonalNumber())){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("The personal number cannot be changed");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        User existingUser = optionalUser.get();

        modelMapper.map(userDTO, existingUser);

        User updatedUser = userRepository.save(existingUser);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("User with id: " + updatedUser.getId() + " updated successfully");
        successes.add(successDTO);
        responseBody.setSuccess(successes);

        return responseBody;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public User getCardsByUserId(Long userId, Long cardId) {

        return userRepository.getCardsByUserId(userId, cardId);
    }

    @Override
    public User getReviewByUserId(Long userId, Long reviewId) {

        return userRepository.getReviewsByUserId(userId, reviewId);

    }
}
