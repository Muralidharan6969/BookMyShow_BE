package com.example.bookmyshow_be.Repositories;

import com.example.bookmyshow_be.Models.Theatre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findByTheatreName(String theatreName);
    @Override
    Theatre save(Theatre theatre);

    @Override
    List<Theatre> findAll();

    Page<Theatre> findByIsAdminApproved(boolean isAdminApproved, Pageable pageable);

    @Query("SELECT DISTINCT t FROM Theatre t " +
            "JOIN t.screens sc " +
            "JOIN sc.shows sh " +
            "JOIN sh.movie m " +
            "JOIN t.city c " +
            "WHERE m.movieId = :movieId AND c.cityId = :cityId AND sh.showDate = :date")
    List<Theatre> findTheatresByMovieCityAndDate(
            @Param("movieId") Long movieId,
            @Param("cityId") Long cityId,
            @Param("date") LocalDate date
    );

    @Query("SELECT DISTINCT t FROM Theatre t " +
            "JOIN t.screens sc " +
            "JOIN sc.shows sh " +
            "JOIN sh.movie m " +
            "JOIN t.city c " +
            "WHERE m.movieId = :movieId AND c.cityId = :cityId AND sh.showDate = :date")
    List<Theatre> findTheatresWithShowsByMovieCityAndDate(
            @Param("movieId") Long movieId,
            @Param("cityId") Long cityId,
            @Param("date") LocalDate date
    );
}
