package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Override
    Optional<Movie> findById(Long movieId);
    Optional<Movie> findByMovieName(String movieName);
    Optional<Movie> findByMovieNameContainsIgnoreCase(String movieName);

    @Override
    List<Movie> findAll();

    @Override
    void deleteById(Long movieId);

    Movie save(Movie movie);

    List<Movie> findByMovieNameIn(List<String> movieNames);

    @Query("SELECT DISTINCT new Movie(m.movieId, m.movieName, m.language, m.movieImageURL, " +
            "m.backgroundImageURL, m.releaseDate, m.movieDuration) FROM Movie m " +
            "JOIN Show s ON s.movie.movieId = m.movieId " +
            "JOIN Screen sc ON sc.screenId = s.screen.screenId " +
            "JOIN Theatre t ON t.theatreId = sc.theatre.theatreId " +
            "WHERE t.city.cityId = :cityId")
    Page<Movie> findAllMoviesByCityId(@Param("cityId") Long cityId, Pageable pageable);

    @Query("SELECT DISTINCT new Movie(m.movieId, m.movieName, m.language, m.movieImageURL, " +
            "m.backgroundImageURL, m.releaseDate, m.movieDuration) " +
            "FROM Movie m " +
            "LEFT JOIN Show s ON s.movie.movieId = m.movieId " +
            "WHERE s.showId IS NULL AND m.releaseDate > CURRENT_DATE")
    Page<Movie> findAllUpcomingMoviesByCityId(@Param("cityId") Long cityId, Pageable pageable);
}
