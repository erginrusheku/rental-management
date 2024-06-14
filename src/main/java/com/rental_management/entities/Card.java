package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Card {

    private Long cardId;
    private String cardholderName;
    private String cardNumber; // Encrypted for security
    private Date expirationDate;
    private String cvv; // Encrypted for security
    private String billingAddress;
    private String cardType; // Visa, MasterCard, American Express, etc.
    private boolean isDefaultCard;
    private Date creationDate;
    private Date lastUpdatedDate;
    private User user;
    // Role to be added
}
