package com.rental_management.entities;

import java.util.Date;
import java.util.List;

public class User {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double discountAmount;
    private String offerDetails;
    private Property property;
    private Promotion promotion;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
}
