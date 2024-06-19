package com.rental_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ResponseBody {

    private List<SuccessDTO> success;
    private List<ErrorDTO> error;
}
