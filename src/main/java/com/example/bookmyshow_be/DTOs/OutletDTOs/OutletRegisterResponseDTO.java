package com.example.bookmyshow_be.DTOs.OutletDTOs;

import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutletRegisterResponseDTO {
    private String outletOwnershipName;
    private String email;
    private Long mobileNumber;

    public static OutletRegisterResponseDTO convertOutletToDTO(Outlet outlet){
        OutletRegisterResponseDTO responseDTO = new OutletRegisterResponseDTO();
        responseDTO.setOutletOwnershipName(outlet.getOutletOwnershipName());
        responseDTO.setEmail(outlet.getEmail());
        responseDTO.setMobileNumber(outlet.getMobileNumber());
        return responseDTO;
    }
}
