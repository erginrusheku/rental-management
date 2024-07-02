package com.rental_management.dto;

import com.rental_management.entities.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class BookingDTO {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Status status;
    private int day;
    private int peopleNumber;
}
