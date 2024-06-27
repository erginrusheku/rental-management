package com.rental_management.dto;

import com.rental_management.entities.CardType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CardDTO {

    private String cardNumber;
    private int cvv;
    private CardType cardType;
}
