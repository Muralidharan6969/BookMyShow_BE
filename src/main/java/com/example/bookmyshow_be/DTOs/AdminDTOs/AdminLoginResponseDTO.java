package com.example.bookmyshow_be.DTOs.AdminDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminLoginResponseDTO {
    private String token;

    public static AdminLoginResponseDTO convertTokenStringToDTO(String token){
        return new AdminLoginResponseDTO(token);
    }
}
