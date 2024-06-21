package com.rental_management.dto;

import com.rental_management.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CardDTO {
    private Long userId;
    private String cardholderName;
}
