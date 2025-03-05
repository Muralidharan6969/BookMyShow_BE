package com.example.bookmyshow_be.DTOs.TheatreDTOs;

import com.example.bookmyshow_be.DTOs.MovieDTOs.AddMovieResponseDTO;
import com.example.bookmyshow_be.Models.Movie;
import com.example.bookmyshow_be.Models.Theatre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTheatreResponseDTO {
    private Long theatreId;
    private String theatreName;
    private String theatreAddress;
    private String cityName;
    private String outletName;

    public static AddTheatreResponseDTO convertTheatreToDTO(Theatre theatre){
        AddTheatreResponseDTO responseDTO = new AddTheatreResponseDTO();
        responseDTO.setTheatreId(theatre.getTheatreId());
        responseDTO.setTheatreName(theatre.getTheatreName());
        responseDTO.setTheatreAddress(theatre.getTheatreName());
        responseDTO.setCityName(theatre.getCity().getCityName());
        responseDTO.setOutletName(theatre.getOutlet().getOutletOwnershipName());
        return responseDTO;
    }
}
