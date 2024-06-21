package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardId;
    private String cardholderName;
    private Long cardNumber;
    private String cvv;
    private String cardType;
    private Date creationDate;
    private Date expirationDate;

    @ManyToOne
    private User user;

}
