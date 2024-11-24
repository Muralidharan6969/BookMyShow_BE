package com.example.bookmyshow_be.DTOs;

import com.example.bookmyshow_be.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDTO {
    private String name;
    private String email;
    private Long mobileNumber;
    private String password;

    public static User convertDTOToUser(UserSignUpRequestDTO userSignUpRequestDTO){
        User user = new User();
        user.setName(userSignUpRequestDTO.getName());
        user.setEmail(userSignUpRequestDTO.getEmail());
        user.setMobileNumber(userSignUpRequestDTO.getMobileNumber());
        user.setPassword(userSignUpRequestDTO.getPassword());
        return user;
    }
}