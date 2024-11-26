package com.example.bookmyshow_be.DTOs.AdminDTOs;

import com.example.bookmyshow_be.Models.Admin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterRequestDTO {
    private String registrationId;
    private Long mobileNumber;
    private String password;

    public static Admin convertDTOToAdmin(AdminRegisterRequestDTO adminRegisterRequestDTO){
        Admin admin = new Admin();
        admin.setRegistrationId(adminRegisterRequestDTO.getRegistrationId());
        admin.setMobileNumber(adminRegisterRequestDTO.getMobileNumber());
        admin.setPassword(adminRegisterRequestDTO.getPassword());
        return admin;
    }
}