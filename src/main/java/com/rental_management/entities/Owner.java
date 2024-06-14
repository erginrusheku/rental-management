package com.rental_management.entities;

import java.util.Date;
import java.util.List;

public class Owner {
    private Long id;
    private Property property;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
}
