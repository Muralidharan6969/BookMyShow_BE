package com.example.bookmyshow_be.DTOs.MovieDTOs;

import com.example.bookmyshow_be.Models.Movie;
import com.example.bookmyshow_be.Utils.ENUMS.Genre;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AddMovieRequestDTO {
    private String movieName;
    private String language;
    private String movieImageURL;
    private String backgroundImageURL;
    private LocalDate releaseDate;
    private String movieDuration;
    private String movieDescription;
    private List<Genre> genres;
    private String movieCertificate;

    public static Movie convertDTOToMovie(AddMovieRequestDTO dto) {
        Movie movie = new Movie();
        movie.setMovieName(dto.getMovieName());
        movie.setLanguage(dto.getLanguage());
        movie.setMovieImageURL(dto.getMovieImageURL());
        movie.setBackgroundImageURL(dto.getBackgroundImageURL());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setMovieDuration(dto.getMovieDuration());
        movie.setMovieDescription(dto.getMovieDescription());
        movie.setGenres(dto.getGenres());
        movie.setMovieCertificate(dto.getMovieCertificate());
        return movie;
    }
}
