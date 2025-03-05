package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.MovieDTOs.MovieDTO;
import com.example.bookmyshow_be.DTOs.PaginationRequest;
import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.DTOs.ScreenDTOs.ScreenDTO;
import com.example.bookmyshow_be.DTOs.SeatDTOs.GenerateSeatLayoutDTO;
import com.example.bookmyshow_be.DTOs.SeatDTOs.SeatLayoutResponseDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.BookingDetailsDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.MovieDatesDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.ShowDTO;
import com.example.bookmyshow_be.DTOs.TheatreDTOs.AddTheatreResponseDTO;
import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreDTO;
import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreShowsDTO;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.Theatre;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Services.ScreenService;
import com.example.bookmyshow_be.Services.SeatService;
import com.example.bookmyshow_be.Services.ShowService;
import com.example.bookmyshow_be.Services.TheatreService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/theatres")
public class TheatreController {
    private TheatreService theatreService;
    private ScreenService screenService;
    private SeatService seatService;
    private ShowService showService;

    @Autowired
    public TheatreController(TheatreService theatreService, ScreenService screenService,
                             SeatService seatService, ShowService showService) {
        this.theatreService = theatreService;
        this.screenService = screenService;
        this.seatService = seatService;
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<?> addTheatre(@RequestBody TheatreDTO theatreDTO){
        AddTheatreResponseDTO responseDTO = AddTheatreResponseDTO.convertTheatreToDTO
                (theatreService.addTheatre(theatreDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Theatre created successfully", responseDTO));
    }

    @GetMapping("/approvalList")
    public ResponseEntity<?> getAllTheatresForApproval(@ModelAttribute PaginationRequest paginationRequest){
        List<Theatre> theatres = theatreService.getAllTheatres(
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                paginationRequest.getSortField(),
                paginationRequest.getSortOrder(),
                false
        );

        List<TheatreDTO> theatreDTOs = theatres.stream()
                .map(TheatreDTO::convertTheatreToDTO)
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Theatres fetched successfully", theatreDTOs));
    }

    @GetMapping
    public ResponseEntity<?> getAllTheatres(@ModelAttribute PaginationRequest paginationRequest){
        List<Theatre> theatres = theatreService.getAllTheatres(
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                paginationRequest.getSortField(),
                paginationRequest.getSortOrder(),
                true
        );

        List<TheatreDTO> theatreDTOs = theatres.stream()
                .map(TheatreDTO::convertTheatreToDTO)
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Theatres fetched successfully!", theatreDTOs));
    }

    @PatchMapping("/approve/{theatreId}")
    public ResponseEntity<?> approveTheatre(@PathVariable("theatreId") Long theatreTd){
        theatreService.approveTheatre(theatreTd);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Theatre has been approved!", null));
    }

    @GetMapping("/screens/generate-seat-layout")
    public ResponseEntity<?> generateSeatLayout(@RequestBody GenerateSeatLayoutDTO seatLayoutDTO){
        List<List<SeatLayoutResponseDTO>> responseDTO = seatService.generateSeatLayout(seatLayoutDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Sample Seat Layout generated successfully!", responseDTO));
    }

    @PostMapping("/{theatreId}/screens")
    public ResponseEntity<?> addScreensForTheatre(@RequestBody ScreenDTO screenDTO){
        ScreenDTO responseDTO = ScreenDTO.convertScreenToDTO(screenService.addScreen(screenDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Screen and Seat Layout created successfully", responseDTO));
    }

    @PostMapping("/{theatreId}/screens/{screenId}/shows")
    public ResponseEntity<?> addShowsForScreen(@RequestBody ShowDTO showDTO){
        ShowDTO responseDTO = ShowDTO.convertShowToDTO(showService.addShow(showDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Show and Show Seat Mapping created successfully!", responseDTO));
    }

    @GetMapping("/movies/{movieId}/city/{cityId}/dates")
    public ResponseEntity<?> getAvailableDatesAndDays(@PathVariable Long movieId, @PathVariable Long cityId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper("Available dates listed successfully!",
                        showService.getAvailableDatesAndDaysForMovieAndCity(movieId, cityId)));
    }

    @GetMapping("/movies/{movieId}/city/{cityId}/date/{date}")
    public ResponseEntity<?> getTheatresAndShows(
            @PathVariable Long movieId,
            @PathVariable Long cityId,
            @PathVariable LocalDate date
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper("Available Theatres and Shows listed successfully!",
                        theatreService.getTheatresAndShowsForMovieCityAndDate(movieId, cityId, date)));
    }

    @GetMapping("/shows/{showId}/seat-layout")
    public ResponseEntity<?> getSeatLayoutForShow(@PathVariable Long showId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper("Seat Layout listed successfully!",
                        showService.getSeatLayoutForShow(showId)));
    }

    @PostMapping("/shows/{showId}/block-seats")
    public ResponseEntity<?> blockSelectedSeats(@PathVariable Long showId,
                                                @RequestBody Map<String, List<Long>> request,
                                                        HttpServletRequest httpRequest){
        Object authenticatedUser = httpRequest.getAttribute("authenticatedUser");
        Long userId;
        if (authenticatedUser instanceof User) {
            userId = ((User) authenticatedUser).getUserId();
        } else {
            throw new EntityNotFoundException("Invalid user information");
        }
        List<Long> showSeatIds = request.get("showSeatIds");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper("Seats have been blocked successfully!",
                        showService.blockSelectedSeats(showId, showSeatIds, userId)));
    }
}
