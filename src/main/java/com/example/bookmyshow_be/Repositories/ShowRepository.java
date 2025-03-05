package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Show;
import com.example.bookmyshow_be.Projections.DateInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Override
    Optional<Show> findById(Long aLong);

//    List<Show> findAllByMovie_MovieId(Long movieId);

    @Query("SELECT DISTINCT sh.showDate AS showDate, to_char(sh.showDate, 'Day') AS dayName FROM Show sh " +
            "JOIN sh.screen sc " +
            "JOIN sc.theatre th " +
            "JOIN th.city c " +
            "WHERE sh.movie.movieId = :movieId AND c.cityId = :cityId " +
            "ORDER BY sh.showDate ASC")
    List<DateInfoProjection> findDistinctDatesAndDaysByMovieAndCity(@Param("movieId") Long movieId, @Param("cityId") Long cityId);

    @Query("SELECT sh FROM Show sh " +
            "WHERE sh.screen.theatre.theatreId = :theatreId " +
            "AND sh.movie.movieId = :movieId " +
            "AND sh.showDate = :date")
    List<Show> findShowsByTheatreMovieAndDate(
            @Param("theatreId") Long theatreId,
            @Param("movieId") Long movieId,
            @Param("date") LocalDate date
    );
}
