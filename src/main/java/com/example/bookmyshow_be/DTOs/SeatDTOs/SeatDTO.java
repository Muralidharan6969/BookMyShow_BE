package com.example.bookmyshow_be.DTOs.SeatDTOs;

import com.example.bookmyshow_be.Models.Seat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {
    private Long seatId;
    private String seatNumber;
    private String seatType;

    public static SeatDTO convertSeatToDTO(Seat seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setSeatId(seat.getSeatId());
        seatDTO.setSeatNumber(seat.getSeatNumber());
        seatDTO.setSeatType(seat.getSeatType().name());
        return seatDTO;
    }
}
