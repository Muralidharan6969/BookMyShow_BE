package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.MovieDTOs.AddMovieRequestDTO;
import com.example.bookmyshow_be.DTOs.MovieDTOs.AddMovieResponseDTO;
import com.example.bookmyshow_be.DTOs.MovieDTOs.ListMoviesDTO;
import com.example.bookmyshow_be.DTOs.MovieDTOs.MovieDTO;
import com.example.bookmyshow_be.DTOs.PaginationRequest;
import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.Models.City;
import com.example.bookmyshow_be.Models.Movie;
import com.example.bookmyshow_be.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody AddMovieRequestDTO requestDTO){
        AddMovieResponseDTO responseDTO = AddMovieResponseDTO.convertMovieToDTO
                (movieService.addMovie(AddMovieRequestDTO.convertDTOToMovie(requestDTO)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Movie created successfully", responseDTO));
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateMovie(
            @PathVariable Long movieId,
            @RequestBody Movie movie) {

        Movie updatedMovie = movieService.updateMovie(movieId, movie);

        return ResponseEntity.ok(new ResponseWrapper("Movie updated successfully", updatedMovie));
    }

    @GetMapping
    public ResponseEntity<?> getAllMovies(@ModelAttribute PaginationRequest paginationRequest){
        List<Movie> movies = movieService.findAllMovies(
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                paginationRequest.getSortField(),
                paginationRequest.getSortOrder()
        );

        List<MovieDTO> movieDTOs = movies.stream()
                .map(MovieDTO::convertMovieToDTO)
                .collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Movies fetched successfully", movieDTOs));
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieById(@PathVariable("movieId") Long movieId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Movie fetched successfully",
                        MovieDTO.convertMovieToDTO(movieService.getMovieByMovieId(movieId))));
    }

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<?> getAllMoviesByCity(@ModelAttribute PaginationRequest paginationRequest,
                                                @PathVariable("cityId") Long cityId){
        ListMoviesDTO moviesDTO = movieService.findAllMoviesByCityId(cityId,
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                paginationRequest.getSortField(),
                paginationRequest.getSortOrder()
        );


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Running movies fetched successfully", moviesDTO));
    }

    @GetMapping("/upcoming/cities/{cityId}")
    public ResponseEntity<?> getAllUpcomingMoviesByCity(@ModelAttribute PaginationRequest paginationRequest,
                                                @PathVariable("cityId") Long cityId){
        ListMoviesDTO moviesDTO = movieService.findAllUpcomingMoviesByCityId(cityId,
                paginationRequest.getPage(),
                paginationRequest.getPageSize(),
                paginationRequest.getSortField(),
                paginationRequest.getSortOrder()
        );


        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Upcoming movies fetched successfully", moviesDTO));
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getpopularMovies(){
        ListMoviesDTO moviesDTO = movieService.fetchAllPopularMovies();

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("Popular movies fetched successfully", moviesDTO));
    }
}
