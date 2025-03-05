package com.example.bookmyshow_be.DTOs.AdminDTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginRequestDTO {
    private Long mobileNumber;
    private String password;
}
