package com.rental_management.dto;

import com.rental_management.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CardDTO {
    private String cardholderName;
    private Long cardNumber;
    private String cvv;
    private String cardType;
    private Date creationDate;
    private Date expirationDate;
}
