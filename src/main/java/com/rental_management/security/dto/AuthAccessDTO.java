package com.rental_management.security.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthAccessDTO {

    private String accessToken = "Bearer ";
    private String token;

    public AuthAccessDTO(String token){
        this.token = token;
    }

}
