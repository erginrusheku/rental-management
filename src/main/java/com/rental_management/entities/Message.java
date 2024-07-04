package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    private String userMessage;
    private String ownerMessage;

    @ManyToOne
    private Owner owner;

    @ManyToOne
    private User user;
}
