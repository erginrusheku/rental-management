package com.rental_management.service;

import com.rental_management.dto.ErrorDTO;
import com.rental_management.dto.PeopleDTO;
import com.rental_management.dto.ResponseBody;
import com.rental_management.dto.SuccessDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleServiceImpl implements PeopleService{
    @Override
    public ResponseBody createPeopleByUser(Long userId, List<PeopleDTO> peopleList) {
        ResponseBody responseBody = new ResponseBody();
        List<ErrorDTO> errors = new ArrayList<>();
        List<SuccessDTO> successes = new ArrayList<>();


        return null;
    }
}
