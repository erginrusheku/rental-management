package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    private String content;
    private Timestamp timestamp;
    @ManyToOne
    private User user;
    @ManyToOne
    @JoinColumn(name = "owner_message_id")
    private OwnerMessage replyToOwnerMessage;
}
