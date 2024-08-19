package com.rental_management.security.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import com.rental_management.security.dto.AuthAccessDTO;
import com.rental_management.security.dto.LoginDTO;
import com.rental_management.security.dto.RegisterDTO;
import com.rental_management.security.entity.Role;
import com.rental_management.security.entity.UserSecurity;
import com.rental_management.security.repo.RoleRepository;
import com.rental_management.security.repo.UserSecurityRepository;
import com.rental_management.security.security_config.JWTUtil;
import org.apache.catalina.connector.Response;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserRoleService {

    private final UserSecurityRepository userSecurityRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserRoleService(UserSecurityRepository userSecurityRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userSecurityRepository = userSecurityRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public RegisterDTO registerUser(RegisterDTO registerDTO){

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUsername(registerDTO.getUsername());
        userSecurity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        String roleName = "ROLE_" + registerDTO.getRole();

        Role roles = roleRepository.findByName(roleName);

        userSecurity.setRoleList(Collections.singletonList(roles));

        userSecurityRepository.save(userSecurity);

        return registerDTO;

    }

    public AuthAccessDTO login(LoginDTO loginDTO){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(authentication);

        return new AuthAccessDTO(token);

    }


    public ResponseBody createRole(Role role) {

        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> success = new ArrayList<>();

        String roleNameWithPrefix = "ROLE_" + role.getName();
        role.setName(roleNameWithPrefix);


        if (role.getName() == null || (!role.getName().equals("ROLE_ADMIN") && !role.getName().equals("ROLE_USER"))) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Invalid role. Only 'ROLE_ADMIN' and 'ROLE_USER' are allowed.");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }

        if (roleRepository.existsByName(role.getName())) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setErrors(true);
            errorDTO.setMessage("Role already exists.");
            errors.add(errorDTO);
            responseBody.setError(errors);
            return responseBody;
        }


        roleRepository.save(role);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(true);
        successDTO.setMessage("The role has been created successfully.");
        success.add(successDTO);
        responseBody.setSuccess(success);

        return responseBody;

        }

}
