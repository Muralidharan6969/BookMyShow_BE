package com.example.bookmyshow_be.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDTO {
    private String username;
    private String password;
}
