package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MessageDTO {

    private String content;
    private Timestamp timestamp;

}
