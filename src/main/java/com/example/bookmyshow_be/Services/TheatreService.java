package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.DTOs.ShowDTOs.ListShowDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.ShowDTO;
import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreDTO;
import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreShowsDTO;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.*;
import com.example.bookmyshow_be.Repositories.*;
import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheatreService {
    private TheatreRepository theatreRepository;
    private CityRepository cityRepository;
    private OutletRepository outletRepository;
    private ShowRepository showRepository;
    private final ShowSeatMappingRepository showSeatMappingRepository;

    @Autowired
    public TheatreService(TheatreRepository theatreRepository,
                          CityRepository cityRepository,
                          OutletRepository outletRepository,
                          ShowRepository showRepository,
                          ShowSeatMappingRepository showSeatMappingRepository){
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
        this.outletRepository = outletRepository;
        this.showRepository = showRepository;
        this.showSeatMappingRepository = showSeatMappingRepository;
    }

    public Theatre addTheatre(TheatreDTO theatreDTO){

        Optional<City> cityOptional = cityRepository.findById(theatreDTO.getCityId());
        if(cityOptional.isEmpty()){
            throw new EntityNotFoundException("City not found");
        }
        Optional<Outlet> outletOptional = outletRepository.findById(theatreDTO.getOutletId());
        if(outletOptional.isEmpty()){
            throw new EntityNotFoundException("Outlet not found");
        }

        Theatre theatre = new Theatre();
        theatre.setTheatreName(theatreDTO.getTheatreName());
        theatre.setTheatreAddress(theatreDTO.getTheatreAddress());
        theatre.setCity(cityOptional.get());
        theatre.setOutlet(outletOptional.get());
        return theatreRepository.save(theatre);
    }

    public Theatre updateTheatre(Long theatreId, TheatreDTO theatreDTO){
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
        if(theatreOptional.isEmpty()){
            throw new EntityNotFoundException("Theatre not found");
        }
        Optional<City> cityOptional = cityRepository.findById(theatreDTO.getCityId());
        if(cityOptional.isEmpty()){
            throw new EntityNotFoundException("City not found");
        }
        Theatre theatre = theatreOptional.get();
        theatre.setTheatreName(theatreDTO.getTheatreName());
        theatre.setTheatreAddress(theatreDTO.getTheatreAddress());
        theatre.setCity(cityOptional.get());
        return theatreRepository.save(theatre);
    }

    public void deleteTheatre(Long theatreId){
        theatreRepository.deleteById(theatreId);
    }

    public Theatre getTheatreByName(String theatreName){
        Optional<Theatre> theatreOptional = theatreRepository.findByTheatreName(theatreName);
        if(theatreOptional.isEmpty()){
            throw new EntityNotFoundException("Theatre not found");
        }
        return theatreOptional.get();
    }

    public Theatre getTheatreById(Long theatreId){
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
        if(theatreOptional.isEmpty()){
            throw new EntityNotFoundException("Theatre not found");
        }
        return theatreOptional.get();
    }

    public List<Theatre> getAllTheatres(int page, int pageSize, String sortField, String sortOrder,
                                        Boolean isAdminApproved){
        Sort.Direction direction =
                sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortField));
        Page<Theatre> theatrePage;
        if (isAdminApproved == null) {
            theatrePage = theatreRepository.findAll(pageable); // Fetch all without filter
        } else {
            theatrePage = theatreRepository.findByIsAdminApproved(isAdminApproved, pageable); // Fetch with filter
        }

        return theatrePage.getContent();
    }

    public void approveTheatre(Long theatreId){
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
        if(theatreOptional.isEmpty()){
            throw new EntityNotFoundException("Theatre not found");
        }
        Theatre theatre = theatreOptional.get();
        theatre.setAdminApproved(true);
        theatreRepository.save(theatre);
    }

//    @Transactional(readOnly = true)
//    public List<TheatreShowsDTO> getTheatresAndShowsForMovieCityAndDate(Long movieId, Long cityId, LocalDate date) {
//        List<Theatre> theatres = theatreRepository.findTheatresByMovieCityAndDate(movieId, cityId, date);
//
//        return theatres.stream().map(theatre -> {
//            List<Show> shows = showRepository.findShowsByTheatreMovieAndDate(theatre.getTheatreId(), movieId, date);
//
//            List<ListShowDTO> showDTOs = shows.stream().map(show -> new ListShowDTO(
//                    show.getShowId(),
//                    show.getShowStartTime().toString()
//            )).collect(Collectors.toList());
//
//            return new TheatreShowsDTO(
//                    theatre.getTheatreName(),
//                    theatre.getTheatreAddress(),
//                    List.of("M-Ticket", "Food & Beverage"),
//                    showDTOs
//            );
//        }).collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public List<TheatreShowsDTO> getTheatresAndShowsForMovieCityAndDate(Long movieId, Long cityId, LocalDate date) {
        List<Theatre> theatres = theatreRepository.findTheatresWithShowsByMovieCityAndDate(movieId, cityId, date);

        return theatres.stream().map(theatre -> {

            List<ListShowDTO> showDTOs = theatre.getScreens().stream()
                    .flatMap(screen -> screen.getShows().stream())
                    .filter(show -> show.getMovie().getMovieId().equals(movieId) && show.getShowDate().equals(date))
                    .map(show -> {
                        Long totalSeats = show.getScreen().getTotalSeats();
                        Long bookedSeats = showSeatMappingRepository.countBookedSeatsForShow(show.getShowId(), SeatStatus.AVAILABLE);
                        long availableSeats = totalSeats - bookedSeats;

                        double availabilityPercentage = ((double) (totalSeats - bookedSeats) / totalSeats) * 100;

                        String availabilityStatus;
                        if (bookedSeats.equals(totalSeats)) {
                            availabilityStatus = "Unavailable";
                        } else if (availabilityPercentage < 40) {
                            availabilityStatus = "Fast Filling";
                        } else {
                            availabilityStatus = "Available";
                        }

                        return new ListShowDTO(
                                show.getShowId(),
                                show.getShowStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")), availabilityStatus, show.getScreen().getScreenName());
                            }
                    ).collect(Collectors.toList());

            return new TheatreShowsDTO(
                    theatre.getTheatreName(),
                    theatre.getTheatreAddress(),
                    List.of("M-Ticket", "Food & Beverage"),
                    showDTOs
            );
        }).collect(Collectors.toList());
    }
}
