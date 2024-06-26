package com.rental_management.service;

import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.UserDTO;
import com.rental_management.entities.User;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    ResponseBody createUser(UserDTO userDTO);
    ResponseBody updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long id);
    User getCardsByUserId(Long userId, Long cardId);
    User getReviewByUserId(Long userId, Long reviewId);
}
