package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.DTOs.ScreenDTOs.ScreenDTO;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.Screen;
import com.example.bookmyshow_be.Models.Theatre;
import com.example.bookmyshow_be.Repositories.ScreenRepository;
import com.example.bookmyshow_be.Repositories.TheatreRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ScreenService {
    private ScreenRepository screenRepository;
    private TheatreRepository theatreRepository;
    private SeatService seatService;

    @Autowired
    public ScreenService(ScreenRepository screenRepository,
                         TheatreRepository theatreRepository,
                         SeatService seatService){
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
        this.seatService = seatService;
    }

    @Transactional
    public Screen addScreen(ScreenDTO screenDTO){
        Optional<Theatre> theatreOptional = theatreRepository.findById(screenDTO.getTheatreId());
        if(theatreOptional.isEmpty()){
            throw new EntityNotFoundException("Theatre not found");
        }
        Screen screen = new Screen();
        screen.setScreenName(screenDTO.getScreenName());
        screen.setTotalSeats(screenDTO.getTotalSeats());
        screen.setNoOfRows(screenDTO.getNoOfRows());
        screen.setNoOfColumns(screenDTO.getNoOfColumns());
        screen.setNoOfPremiumRows(screenDTO.getNoOfPremiumRows());
        screen.setTheatre(theatreOptional.get());
        Screen savedScreen = screenRepository.save(screen);
        seatService.addSeatLayout(savedScreen);
//      savedScreen.setSeatList(savedScreen.getSeatList()); Not sure whether to add this line
        return screenRepository.save(savedScreen);
    }
}
