package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserDTO {
    private String userName;
    private String userLastName;
    private String email;
    private int peopleNumber;
}
