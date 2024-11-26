package com.example.bookmyshow_be.DTOs.AdminDTOs;

import com.example.bookmyshow_be.Models.Admin;
import com.example.bookmyshow_be.Models.Outlet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterResponseDTO {
    private String registrationId;
    private String email;
    private Long mobileNumber;

    public static AdminRegisterResponseDTO convertAdminToDTO(Admin admin){
        AdminRegisterResponseDTO responseDTO = new AdminRegisterResponseDTO();
        responseDTO.setRegistrationId(admin.getRegistrationId());
        responseDTO.setMobileNumber(admin.getMobileNumber());
        return responseDTO;
    }
}
