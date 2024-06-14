package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {

    private Long cardId;
    private String userName;
    private String email;
    private String password;
    // Role to be added
}
