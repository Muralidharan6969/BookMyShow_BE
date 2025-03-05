package com.example.bookmyshow_be.DTOs.MovieDTOs;

import com.example.bookmyshow_be.Models.Movie;
import com.example.bookmyshow_be.Utils.ENUMS.Genre;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieDTO {
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

    public static MovieDTO convertMovieToDTO(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovieId(movie.getMovieId());
        movieDTO.setMovieName(movie.getMovieName());
        movieDTO.setLanguage(movie.getLanguage());
        movieDTO.setMovieImageURL(movie.getMovieImageURL());
        movieDTO.setBackgroundImageURL(movie.getBackgroundImageURL());
        movieDTO.setReleaseDate(movie.getReleaseDate());
        movieDTO.setMovieDuration(movie.getMovieDuration());
        movieDTO.setMovieDescription(movie.getMovieDescription());
        movieDTO.setGenres(movie.getGenres());
        movieDTO.setMovieCertificate(movie.getMovieCertificate());
        return movieDTO;
    }
}
