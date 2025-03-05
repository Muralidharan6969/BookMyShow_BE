package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.DTOs.MovieDTOs.ListMoviesDTO;
import com.example.bookmyshow_be.DTOs.MovieDTOs.MovieDTO;
import com.example.bookmyshow_be.Exceptions.EntityNotFoundException;
import com.example.bookmyshow_be.Models.Movie;
import com.example.bookmyshow_be.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> findAllMovies(int page, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction =
                sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortField));
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        System.out.println("moviePage" + moviePage.getContent());
        return moviePage.getContent();
    }

    public Movie updateMovie(Long movieId, Movie updatedMovie) {
        return movieRepository.findById(movieId).map(existingMovie -> {
            Field[] fields = Movie.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object newValue = field.get(updatedMovie);
                    if (newValue != null) {
                        field.set(existingMovie, newValue);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error updating field: " + field.getName(), e);
                }
            }
            return movieRepository.save(existingMovie);
        }).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
    }

    public void deleteMovie(Long movieId){
        movieRepository.deleteById(movieId);
    }

    public Movie getMovieByMovieName(String movieName){
        Optional<Movie> movieOptional = movieRepository.findByMovieName(movieName);
        if(movieOptional.isEmpty()){
            throw new EntityNotFoundException("Movie not found");
        }
        return movieOptional.get();
    }

    public Movie getMovieByMovieId(Long movieId){
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if(movieOptional.isEmpty()){
            throw new EntityNotFoundException("Movie not found");
        }
        return movieOptional.get();
    }

    public ListMoviesDTO findAllMoviesByCityId(Long cityId, int page, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        List<String> allowedSortFields = Arrays.asList("movieId", "movieName", "releaseDate");
        if (!allowedSortFields.contains(sortField)) {
            sortField = "movieId";
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortField));
        Page<Movie> moviePage = movieRepository.findAllMoviesByCityId(cityId, pageable);
        List<Movie> movies = moviePage.getContent();

        Set<String> languages = movies.stream()
                .map(Movie::getLanguage)
                .collect(Collectors.toCollection(HashSet::new));

        return new ListMoviesDTO(
                languages.toArray(new String[0]),
                movies.stream()
                        .map(MovieDTO::convertMovieToDTO)
                        .collect(Collectors.toList())
        );
    }

    public ListMoviesDTO findAllUpcomingMoviesByCityId(Long cityId, int page, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        List<String> allowedSortFields = Arrays.asList("movieId", "movieName", "releaseDate");
        if (!allowedSortFields.contains(sortField)) {
            sortField = "movieId";
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortField));
        Page<Movie> moviePage = movieRepository.findAllUpcomingMoviesByCityId(cityId, pageable);
        List<Movie> movies = moviePage.getContent();

        Set<String> languages = movies.stream()
                .map(Movie::getLanguage)
                .collect(Collectors.toCollection(HashSet::new));

        return new ListMoviesDTO(
                languages.toArray(new String[0]),
                movies.stream()
                        .map(MovieDTO::convertMovieToDTO)
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public ListMoviesDTO fetchAllPopularMovies(){
        List<String> movieNames = Arrays.asList("Thani Oruvan", "LEO", "Dragon", "Interstellar");
        List<Movie> movies = movieRepository.findByMovieNameIn(movieNames);
        return new ListMoviesDTO(
                null,
                movies.stream()
                        .map(MovieDTO::convertMovieToDTO)
                        .collect(Collectors.toList())
        );
    }
}
