package com.example.bookmyshow_be.DTOs.OutletDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OutletLoginResponseDTO {
    private String token;

    public static OutletLoginResponseDTO convertTokenStringToDTO(String token){
        return new OutletLoginResponseDTO(token);
    }
}
