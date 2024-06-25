package com.rental_management.dto;

import com.rental_management.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeopleDTO {

    private int peopleNumber;
}
