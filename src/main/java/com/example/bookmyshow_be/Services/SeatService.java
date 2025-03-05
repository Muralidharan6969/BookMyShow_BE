package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.DTOs.SeatDTOs.GenerateSeatLayoutDTO;
import com.example.bookmyshow_be.DTOs.SeatDTOs.SeatLayoutResponseDTO;
import com.example.bookmyshow_be.Models.Screen;
import com.example.bookmyshow_be.Models.Seat;
import com.example.bookmyshow_be.Repositories.SeatRepository;
import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    private SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void addSeatLayout(Screen screen) {
        for(long i=1; i<=screen.getNoOfPremiumRows(); i++){
            for(long j=1; j<=screen.getNoOfColumns(); j++){
                screen.getSeatList().add(addSeat(i, j, screen, SeatType.PREMIUM));
            }
        }

        for(long i=screen.getNoOfPremiumRows()+1; i<=screen.getNoOfRows(); i++){
            for(long j=1; j<=screen.getNoOfColumns(); j++){
                screen.getSeatList().add(addSeat(i, j, screen, SeatType.ELITE));
            }
        }
    }

    public List<List<SeatLayoutResponseDTO>> generateSeatLayout(GenerateSeatLayoutDTO seatLayoutDTO){
        List<List<SeatLayoutResponseDTO>> seatLayout = new ArrayList<>();

        for(long i=1; i<=seatLayoutDTO.getNoOfPremiumRows(); i++){
            List<SeatLayoutResponseDTO> row = new ArrayList<>();
            for(long j=1; j<=seatLayoutDTO.getNoOfColumns(); j++){
                row.add(new SeatLayoutResponseDTO(getRCSeatNumber(i, j), SeatType.PREMIUM));
            }
            seatLayout.add(row);
        }

        for(long i=seatLayoutDTO.getNoOfPremiumRows()+1; i<=seatLayoutDTO.getNoOfRows(); i++){
            List<SeatLayoutResponseDTO> row = new ArrayList<>();
            for(long j=1; j<=seatLayoutDTO.getNoOfColumns(); j++){
                row.add(new SeatLayoutResponseDTO(getRCSeatNumber(i, j), SeatType.ELITE));
            }
            seatLayout.add(row);
        }

        return seatLayout;
    }

    public Seat addSeat(long seatRow, long seatColumn, Screen screen, SeatType seatType){
        Seat seat = new Seat();
        seat.setSeatRow(seatRow);
        seat.setSeatCol(seatColumn);
        seat.setScreen(screen);
        seat.setSeatType(seatType);
        seat.setSeatNumber(getRCSeatNumber(seatRow, seatColumn));
        return seatRepository.save(seat);
    }

    public String getRCSeatNumber(long row, long col){
        String rowString = getCharForNumber(row);
        String colString = Long.toString(col);
        return rowString + colString;
    }

    public String getCharForNumber(long i) {
        StringBuffer sb = new StringBuffer();
        while (i > 0) {
            long rem = i % 26;
            if (rem == 0) {
                sb.append('Z');
                i = (i / 26) - 1;
            } else {
                sb.append((char)((rem - 1) + 'A'));
                i = i / 26;
            }
        }
        return sb.reverse().toString();
    }
}
