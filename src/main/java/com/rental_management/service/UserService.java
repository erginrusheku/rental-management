package com.rental_management.service;

import com.rental_management.dto.UserDTO;
import com.rental_management.entities.User;

import java.util.List;

public interface UserService {
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long id);
}
