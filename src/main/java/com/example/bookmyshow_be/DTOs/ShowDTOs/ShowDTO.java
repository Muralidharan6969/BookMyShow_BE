package com.example.bookmyshow_be.DTOs.ShowDTOs;

import com.example.bookmyshow_be.DTOs.ScreenDTOs.ScreenDTO;
import com.example.bookmyshow_be.Models.Screen;
import com.example.bookmyshow_be.Models.Show;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowDTO {
    private Long showId;
    private Long movieId;
    private String movieName;
    private Long screenId;
    private String showDate;
    private String showStartTime;
    private String theatreName;
    private List<ShowSeatTypeDTO> seatTypePriceList;

    public static ShowDTO convertShowToDTO(Show show){
        ShowDTO showDTO = new ShowDTO();
        showDTO.setShowId(show.getShowId());
        showDTO.setMovieId(show.getMovie().getMovieId());
        showDTO.setMovieName(show.getMovie().getMovieName());
        showDTO.setScreenId(show.getScreen().getScreenId());
        showDTO.setShowDate(String.valueOf(show.getShowDate()));
        showDTO.setShowStartTime(String.valueOf(show.getShowStartTime()));
        showDTO.setTheatreName(show.getScreen().getTheatre().getTheatreName());
        return showDTO;
    }
}
