package com.rental_management.service;

import com.rental_management.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
