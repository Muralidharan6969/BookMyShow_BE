package com.example.bookmyshow_be.DTOs.UserDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO {
    private String token;
    private Long userId;
    private String name;
    private String email;
    private Long mobileNumber;

    public static UserLoginResponseDTO convertUserToDTO(String token, Long userId, String name, String email, Long mobileNumber){
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setUserId(userId);
        userLoginResponseDTO.setToken(token);
        userLoginResponseDTO.setName(name);
        userLoginResponseDTO.setEmail(email);
        userLoginResponseDTO.setMobileNumber(mobileNumber);
        return userLoginResponseDTO;
    }
}
