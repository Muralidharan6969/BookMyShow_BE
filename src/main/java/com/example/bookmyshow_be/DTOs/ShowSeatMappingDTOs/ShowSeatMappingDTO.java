package com.example.bookmyshow_be.DTOs.ShowSeatMappingDTOs;

import com.example.bookmyshow_be.DTOs.SeatDTOs.SeatDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.ShowDTO;
import com.example.bookmyshow_be.Models.ShowSeatMapping;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowSeatMappingDTO {
    private Long showSeatMappingId;
    private SeatDTO seat;

    public static ShowSeatMappingDTO convertShowSeatMappingToDTO(ShowSeatMapping seatMapping) {
        ShowSeatMappingDTO seatMappingDTO = new ShowSeatMappingDTO();
        seatMappingDTO.setShowSeatMappingId(seatMapping.getShowSeatMappingId());
        seatMappingDTO.setSeat(SeatDTO.convertSeatToDTO(seatMapping.getSeat()));
        return seatMappingDTO;
    }
}
