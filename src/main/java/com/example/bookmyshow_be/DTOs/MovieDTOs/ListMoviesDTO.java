package com.example.bookmyshow_be.DTOs.MovieDTOs;

import com.example.bookmyshow_be.Models.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListMoviesDTO {
    private String[] languages;
    private List<MovieDTO> moviesArray;
}
