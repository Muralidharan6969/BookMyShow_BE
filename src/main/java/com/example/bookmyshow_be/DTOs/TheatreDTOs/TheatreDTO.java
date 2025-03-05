package com.example.bookmyshow_be.DTOs.TheatreDTOs;

import com.example.bookmyshow_be.Models.Theatre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreDTO {
    private Long theatreId;
    private String theatreName;
    private String theatreAddress;
    private Long cityId;
    private Long outletId;
    private String cityName;
    private String outletName;
    private boolean isAdminApproved;


    public static TheatreDTO convertTheatreToDTO(Theatre theatre){
        TheatreDTO theatreDTO = new TheatreDTO();
        theatreDTO.setTheatreId(theatre.getTheatreId());
        theatreDTO.setTheatreName(theatre.getTheatreName());
        theatreDTO.setTheatreAddress(theatre.getTheatreName());
        theatreDTO.setCityName(theatre.getCity().getCityName());
        theatreDTO.setOutletName(theatre.getOutlet().getOutletOwnershipName());
        theatreDTO.setAdminApproved(theatre.isAdminApproved());
        return theatreDTO;
    }
}
