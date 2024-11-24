package com.example.bookmyshow_be.DTOs;

import com.example.bookmyshow_be.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpResponseDTO {
    private String name;
    private String email;
    private Long mobileNumber;

    public static UserSignUpResponseDTO convertUserToDTO(User user){
        UserSignUpResponseDTO responseDTO = new UserSignUpResponseDTO();
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setMobileNumber(user.getMobileNumber());
        return responseDTO;
    }
}
