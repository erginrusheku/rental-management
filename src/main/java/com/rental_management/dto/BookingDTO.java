package com.rental_management.dto;

import com.rental_management.entities.Status;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class BookingDTO {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Status status;
    private int day;
    private int peopleNumber;
}
