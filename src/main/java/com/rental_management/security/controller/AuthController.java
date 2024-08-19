package com.rental_management.security.controller;

import com.rental_management.dto.ResponseBody;
import com.rental_management.security.dto.AuthAccessDTO;
import com.rental_management.security.dto.LoginDTO;
import com.rental_management.security.dto.RegisterDTO;
import com.rental_management.security.entity.Role;
import com.rental_management.security.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final UserRoleService userRoleService;

    public AuthController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {

        RegisterDTO registerDTOs = userRoleService.registerUser(registerDTO);
        if (registerDTOs != null) {
            return new ResponseEntity<>("User registered successfully ", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("User not registered", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthAccessDTO> login(@RequestBody LoginDTO loginDTO) {

        AuthAccessDTO authAccessDTO = userRoleService.login(loginDTO);

        return new ResponseEntity<>(authAccessDTO, HttpStatus.OK);
    }

    @PostMapping("/createRole")
    public ResponseEntity<ResponseBody> createRole(@RequestBody Role role) {

        ResponseBody responseBody = userRoleService.createRole(role);

        if (responseBody.getError() != null && !responseBody.getError().isEmpty()) {

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }
}