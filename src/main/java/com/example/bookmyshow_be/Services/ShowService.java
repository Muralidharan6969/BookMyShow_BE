package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.DTOs.SeatDTOs.SeatLayoutForShowResponseDTO;
import com.example.bookmyshow_be.DTOs.SeatDTOs.SeatLayoutResponseDTO;
import com.example.bookmyshow_be.DTOs.ShowDTOs.*;
import com.example.bookmyshow_be.DTOs.TheatreDTOs.TheatreDTO;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.*;
import com.example.bookmyshow_be.Projections.DateInfoProjection;
import com.example.bookmyshow_be.Repositories.*;
import com.example.bookmyshow_be.Utils.ENUMS.SeatStatus;
import com.example.bookmyshow_be.Utils.ENUMS.SeatType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShowService {
    private MovieRepository movieRepository;
    private ScreenRepository screenRepository;
    private ShowRepository showRepository;
    private SeatRepository seatRepository;
    private ShowSeatTypeRepository showSeatTypeRepository;
    private ShowSeatMappingRepository showSeatMappingRepository;
    private SeatBlockingRepository seatBlockingRepository;

    @Autowired
    public ShowService(MovieRepository movieRepository,
                       ScreenRepository screenRepository,
                       ShowRepository showRepository,
                       SeatRepository seatRepository,
                       ShowSeatTypeRepository showSeatTypeRepository,
                       ShowSeatMappingRepository showSeatMappingRepository,
                       SeatBlockingRepository seatBlockingRepository) {
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.showSeatTypeRepository = showSeatTypeRepository;
        this.showSeatMappingRepository = showSeatMappingRepository;
        this.seatBlockingRepository = seatBlockingRepository;
    }

    @Transactional
    public Show addShow(ShowDTO showDTO){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
        Show show = new Show();
        show.setShowDate(LocalDate.parse(showDTO.getShowDate(), dateFormatter));
        show.setShowStartTime(LocalTime.parse(showDTO.getShowStartTime(), timeFormatter));

        Optional<Movie> movieOptional = movieRepository.findById(showDTO.getMovieId());
        if(movieOptional.isEmpty()){
            throw new EntityNotFoundException("Movie not found");
        }
        show.setMovie(movieOptional.get());

        Optional<Screen> screenOptional = screenRepository.findById(showDTO.getScreenId());
        if(screenOptional.isEmpty()){
            throw new EntityNotFoundException("Screen not found");
        }
        show.setScreen(screenOptional.get());

        addShowSeatType(showRepository.save(show), showDTO);

        addShowSeatMapping(showRepository.save(show), screenOptional.get().getSeatList());

        return showRepository.save(show);
    }

    private void addShowSeatType(Show show, ShowDTO showDTO) {
        for(ShowSeatTypeDTO showSeatTypeDTO : showDTO.getSeatTypePriceList()){
            ShowSeatType showSeatType = new ShowSeatType();
            showSeatType.setSeatType(showSeatTypeDTO.getSeatType());
            showSeatType.setPrice(showSeatTypeDTO.getPrice());
            showSeatType.setShow(show);
            showSeatTypeRepository.save(showSeatType);
        }
    }

    private void addShowSeatMapping(Show show, List<Seat> seatList) {
        for(Seat seat : seatList){
            ShowSeatMapping showSeatMapping = new ShowSeatMapping();
            showSeatMapping.setShow(show);
            showSeatMapping.setSeat(seat);
            showSeatMapping.setSeatStatus(SeatStatus.AVAILABLE);
            showSeatMappingRepository.save(showSeatMapping);
        }
    }

    public List<MovieDatesDTO> getAvailableDatesAndDaysForMovieAndCity(Long movieId, Long cityId){
        List<DateInfoProjection> results = showRepository.findDistinctDatesAndDaysByMovieAndCity(movieId, cityId);

        return results.stream().map(result -> {
            LocalDate date = result.getShowDate();
            String dayOfWeek = result.getDayName().trim().substring(0, 3).toUpperCase();
            int dayOfMonth = date.getDayOfMonth();
            String month = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
            return new MovieDatesDTO(date, dayOfWeek, dayOfMonth, month);
        }).collect(Collectors.toList());
    }

    public ShowDetailsDTO getShowDetails(Long showId) {
        Optional<Show> showOpt = showRepository.findById(showId);

        if (showOpt.isEmpty()) {
            throw new RuntimeException("Show not found");
        }

        Show show = showOpt.get();
        String movieName = show.getMovie().getMovieName();
        String theatreDetail = show.getScreen().getTheatre().getTheatreName() +
                ": " + show.getScreen().getTheatre().getTheatreAddress();

        String showDate = show.getShowDate().format(DateTimeFormatter.ofPattern("EEEE, dd MMM"));
        String showTime = show.getShowStartTime().format(DateTimeFormatter.ofPattern("hh:mm a"));

        String showDetail = showDate + ", " + showTime;

        return new ShowDetailsDTO(movieName, theatreDetail, showDetail);
    }

    @Transactional
    public List<SeatLayoutForShowResponseDTO> getSeatLayoutForShow(Long showId) {
        List<ShowSeatMapping> seatMappings = showSeatMappingRepository.findByShow_ShowId(showId);

        List<ShowSeatType> seatTypes = showSeatTypeRepository.findByShow_ShowId(showId);

        Map<SeatType, Double> seatTypePriceMap = seatTypes.stream()
                .collect(Collectors.toMap(ShowSeatType::getSeatType, ShowSeatType::getPrice));

        return seatMappings.stream()
                .map(mapping -> {
                    SeatLayoutForShowResponseDTO seatLayout = new SeatLayoutForShowResponseDTO();
                    seatLayout.setShowSeatMappingId(mapping.getShowSeatMappingId());
                    seatLayout.setSeatNumber(mapping.getSeat().getSeatNumber());
                    seatLayout.setSeatRow(mapping.getSeat().getSeatRow());
                    seatLayout.setSeatCol(mapping.getSeat().getSeatCol());
                    seatLayout.setSeatType(mapping.getSeat().getSeatType());
                    seatLayout.setSeatStatus(mapping.getSeatStatus());
                    seatLayout.setPrice(seatTypePriceMap.getOrDefault(mapping.getSeat().getSeatType(), 0.0));
                    return seatLayout;
                })
                .sorted(Comparator.comparing(SeatLayoutForShowResponseDTO::getShowSeatMappingId))
                .collect(Collectors.toList());
    }

    private String generateShortBookingId(Long userId, Long showId, String seatNumbers) {
        // Convert IDs to Base36 for shorter representation
        String userBase36 = Long.toString(userId, 36).toUpperCase();
        String showBase36 = Long.toString(showId, 36).toUpperCase();

        // Generate hash for seat numbers
        String seatHash = hashSeatNumbers(seatNumbers);

        // Use Epoch seconds in Base36 for a short timestamp
        String timestamp = Long.toString(Instant.now().getEpochSecond(), 36).toUpperCase();

        // Final Booking ID
        return String.format("WX-%s-%s-%s-%s", userBase36, showBase36, seatHash, timestamp);
    }

    private String hashSeatNumbers(String seatNumbers) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(seatNumbers.getBytes(StandardCharsets.UTF_8));
            // Convert first few bytes to Base36 to keep it short
            long shortHash = ((long) hash[0] & 0xFF) << 8 | ((long) hash[1] & 0xFF);
            return Long.toString(shortHash, 36).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating seat hash", e);
        }
    }


    @Transactional
    public BookingDetailsDTO blockSelectedSeats(Long showId, List<Long> showSeatIds, Long userId){
        List<ShowSeatMapping> seats = showSeatMappingRepository.findAllByShowSeatMappingIdIn(showSeatIds);

        for (ShowSeatMapping seat : seats) {
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new EntityNotFoundException("Selected seats are no longer available!");
            }
        }

        seats.forEach(seat -> {
            seat.setSeatStatus(SeatStatus.BLOCKED);
            seat.setBlockedAt(LocalDateTime.now());
        });

        showSeatMappingRepository.saveAll(seats);

        SeatBlocking seatBlocking = new SeatBlocking();
        seatBlocking.setUserId(userId);
        seatBlocking.setSeatIds(showSeatIds);
        seatBlocking.setExpiryTime(LocalDateTime.now().plusMinutes(10));

        seatBlockingRepository.save(seatBlocking);

        List<String> seatNumbers = seats.stream()
                .map(seat -> seat.getSeat().getSeatNumber())
                .sorted()
                .collect(Collectors.toList());
        String seatNumbersString = String.join("-", seatNumbers);

        String bookingId = generateShortBookingId(userId, showId, seatNumbersString);

        Show show = showRepository.findById(showId).orElseThrow(() -> new EntityNotFoundException("Show not found"));
        Movie movie = show.getMovie();
        Theatre theatre = show.getScreen().getTheatre();

        String formattedDate = show.getShowDate().format(DateTimeFormatter.ofPattern("EEE, d MMM, yyyy"));
        String formattedTime = show.getShowStartTime().format(DateTimeFormatter.ofPattern("hh:mm a"));

        List<ShowSeatType> seatTypes = showSeatTypeRepository.findByShow_ShowId(showId);

        Map<SeatType, Double> seatPriceMap = seatTypes.stream()
                .collect(Collectors.toMap(ShowSeatType::getSeatType, ShowSeatType::getPrice));

        Map<SeatType, List<ShowSeatMapping>> seatsByType = seats.stream()
                .collect(Collectors.groupingBy(seat -> seat.getSeat().getSeatType()));

        List<SeatCategoryDTO> seatCategories = seatsByType.entrySet().stream()
                .map(entry -> new SeatCategoryDTO(
                        entry.getKey(),
                        entry.getValue().stream().map(seat -> seat.getSeat().getSeatNumber()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        List<CategoryPriceDTO> categoryPrices = seatsByType.entrySet().stream()
                .map(entry -> {
                    SeatType seatType = entry.getKey();
                    int count = entry.getValue().size();
                    double pricePerSeat = seatPriceMap.getOrDefault(seatType, 0.0); // Fetch price from ShowSeatType
                    return new CategoryPriceDTO(seatType, count, pricePerSeat, count * pricePerSeat);
                })
                .collect(Collectors.toList());

        double totalTicketPrice = categoryPrices.stream().mapToDouble(CategoryPriceDTO::getTotalPrice).sum();
        double totalConvenienceFee = seats.size() * 35.40;
        double totalAmount = totalTicketPrice + totalConvenienceFee;


        return new BookingDetailsDTO(
                bookingId,
                new MovieDetailsDTO(movie.getMovieImageURL(), movie.getMovieName(), movie.getMovieCertificate()),
                new TheatresDTO(theatre.getTheatreName(), theatre.getTheatreAddress()),
                new ShowDetailssDTO(formattedDate, formattedTime, seatCategories),
                new BookingSummaryDTO(
                        categoryPrices, totalConvenienceFee, totalAmount
                )
        );
    }

    public boolean validateSession(Long userId, List<Long> seatIds) {
        SeatBlocking seatBlocking = seatBlockingRepository.findByUserIdAndSeatIdsAndExpiryTimeAfter(userId, seatIds, LocalDateTime.now())
                .orElseThrow(() -> new EntityNotFoundException("No active session found for the selected seats"));

        return true;
    }

    @Transactional
    public void releaseSeats(Long userId, List<Long> seatIds) {
        SeatBlocking seatBlocking = seatBlockingRepository.findByUserIdAndSeatIdsAndExpiryTimeAfter(userId, seatIds, LocalDateTime.now())
                .orElseThrow(() -> new EntityNotFoundException("No active session found for the selected seats"));

        List<ShowSeatMapping> seats = showSeatMappingRepository.findAllById(seatBlocking.getSeatIds());
        seats.forEach(seat -> seat.setSeatStatus(SeatStatus.AVAILABLE));
        showSeatMappingRepository.saveAll(seats);

        seatBlockingRepository.delete(seatBlocking);
    }

    public SeatBlocking getSeatBlockingByUserId(Long userId){
        Optional<SeatBlocking> seatBlockingOptional = seatBlockingRepository.findByUserId(userId);

        if(seatBlockingOptional.isEmpty()){
            throw new EntityNotFoundException("No active session found");
        }

        return seatBlockingOptional.get();
    }
}
