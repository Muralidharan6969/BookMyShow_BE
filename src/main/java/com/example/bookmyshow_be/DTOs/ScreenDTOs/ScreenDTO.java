package com.example.bookmyshow_be.DTOs.ScreenDTOs;

import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreDTO;
import com.example.bookmyshow_be.Models.Screen;
import com.example.bookmyshow_be.Models.Theatre;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenDTO {
    private Long screenId;
    private String screenName;
    private Long totalSeats;
    private Long noOfRows;
    private Long noOfColumns;
    private Long noOfPremiumRows;
    private Long theatreId;
    private String startChar;

    public static ScreenDTO convertScreenToDTO(Screen screen){
        ScreenDTO screenDTO = new ScreenDTO();
        screenDTO.setScreenId(screen.getScreenId());
        screenDTO.setTheatreId(screen.getTheatre().getTheatreId());
        screenDTO.setScreenName(screen.getScreenName());
        screenDTO.setNoOfRows(screen.getNoOfRows());
        screenDTO.setNoOfColumns(screen.getNoOfColumns());
        screenDTO.setNoOfPremiumRows(screen.getNoOfPremiumRows());
        screenDTO.setTotalSeats(screen.getTotalSeats());
        return screenDTO;
    }
}
