package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CardDTO {

    private Long cardNumber;
    private int cvv;
    private String cardType;
}
