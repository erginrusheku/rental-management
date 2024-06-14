package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Message {
    private Long messageId;
    private String content;
    private Timestamp timestamp;
    private User user;
    private Owner owner;
}
