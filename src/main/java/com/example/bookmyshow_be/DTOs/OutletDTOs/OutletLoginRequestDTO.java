package com.example.bookmyshow_be.DTOs.OutletDTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletLoginRequestDTO {
    private String email;
    private String password;
}
