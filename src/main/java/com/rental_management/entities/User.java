package com.rental_management.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
public class User {
    private Long id;
    private String userName;
    private String userLastname;
    private String email;
    private Date startDate;
    private Date endDate;
    private double discountAmount;
    private String offerDetails;
    private Property property;
    private Promotion promotion;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
}
