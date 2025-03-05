package com.example.bookmyshow_be.DTOs.MovieDTOs;

import com.example.bookmyshow_be.Models.Movie;
import com.example.bookmyshow_be.Utils.ENUMS.Genre;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AddMovieResponseDTO {
    private Long movieId;
    private String movieName;
    private String language;
    private String movieImageURL;
    private String backgroundImageURL;
    private LocalDate releaseDate;
    private String movieDuration;
    private String movieDescription;
    private List<Genre> genres;
    private String movieCertificate;

    public static AddMovieResponseDTO convertMovieToDTO(Movie movie) {
        AddMovieResponseDTO responseDTO = new AddMovieResponseDTO();
        responseDTO.setMovieId(movie.getMovieId());
        responseDTO.setMovieName(movie.getMovieName());
        responseDTO.setLanguage(movie.getLanguage());
        responseDTO.setMovieImageURL(movie.getMovieImageURL());
        responseDTO.setBackgroundImageURL(movie.getBackgroundImageURL());
        responseDTO.setReleaseDate(movie.getReleaseDate());
        responseDTO.setMovieDuration(movie.getMovieDuration());
        responseDTO.setMovieDescription(movie.getMovieDescription());
        responseDTO.setGenres(movie.getGenres());
        responseDTO.setMovieCertificate(movie.getMovieCertificate());
        return responseDTO;
    }
}
