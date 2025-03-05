package com.example.bookmyshow_be.DTOs.OutletDTOs;

import com.example.bookmyshow_be.DTOs.UserDTOs.UserSignUpRequestDTO;
import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletRegisterRequestDTO {
    private String outletOwnershipName;
    private String email;
    private Long mobileNumber;
    private String password;

    public static Outlet convertDTOToOutlet(OutletRegisterRequestDTO outletRegisterRequestDTO){
        Outlet outlet = new Outlet();
        outlet.setOutletOwnershipName(outletRegisterRequestDTO.getOutletOwnershipName());
        outlet.setEmail(outletRegisterRequestDTO.getEmail());
        outlet.setMobileNumber(outletRegisterRequestDTO.getMobileNumber());
        outlet.setPassword(outletRegisterRequestDTO.getPassword());
        return outlet;
    }
}