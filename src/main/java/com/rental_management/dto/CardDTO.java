package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CardDTO {
    //private String cardholderName;
    private Long cardNumber;
    private int cvv;
    private String cardType;
    private Date creationDate;
    private Date expirationDate;
}
