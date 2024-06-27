package com.rental_management.dto;

import com.rental_management.entities.Status;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class BookingDTO {
    private Date checkInDate;
    private Date checkOutDate;
    private Status status;
    private int day;
    private int peopleNumber;
}
