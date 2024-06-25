package com.rental_management.service;

import com.rental_management.dto.PeopleDTO;
import com.rental_management.dto.ResponseBody;

import java.util.List;

public interface PeopleService {

    ResponseBody createPeopleByUser(Long userId, List<PeopleDTO> peopleList);
}
