package com.example.bookmyshow_be.DTOs.UserDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String token;

    public static UserLoginResponseDTO convertTokenStringToDTO(String token){
        return new UserLoginResponseDTO(token);
    }
}
