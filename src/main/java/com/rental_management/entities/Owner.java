package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class Owner {
    private Long id;
    private String name;
    private int phoneNumber;
    private List<Property> property;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
}
