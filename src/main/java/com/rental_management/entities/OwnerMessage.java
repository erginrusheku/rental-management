package com.rental_management.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class OwnerMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;
    private String content;
    private Timestamp timestamp;

    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "replyToOwnerMessage")
    private List<UserMessage> repliesFromUsers = new ArrayList<>();
}
