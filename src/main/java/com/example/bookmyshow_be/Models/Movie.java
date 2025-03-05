package com.example.bookmyshow_be.Models;

import com.example.bookmyshow_be.Utils.ENUMS.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="movieId")
    private Long movieId;

    @Column(name ="movieName", nullable = false)
    private String movieName;

    @Column(name ="language", nullable = false)
    private String language;

    @Column(name ="movieImageURL", nullable = false)
    private String movieImageURL;

    @Column(name ="backgroundImageURL", nullable = true)
    private String backgroundImageURL;

    @Column(name ="releaseDate", nullable = true)
    private LocalDate releaseDate;

    @Column(name ="movieDuration", nullable = true)
    private String movieDuration;

    @Lob
    @Column(name ="movieDescription", nullable = true, columnDefinition = "TEXT")
    private String movieDescription;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movieId"))
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;

    @Column(name ="movieCertificate", nullable = true)
    private String movieCertificate;

    public Movie(Long movieId, String movieName, String language, String movieImageURL,
                 String backgroundImageURL, LocalDate releaseDate, String movieDuration
                 ) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.language = language;
        this.movieImageURL = movieImageURL;
        this.backgroundImageURL = backgroundImageURL;
        this.releaseDate = releaseDate;
        this.movieDuration = movieDuration;
    }

    public Movie(Long movieId, String movieName, String language, String movieImageURL,
                 String backgroundImageURL, LocalDate releaseDate, String movieDuration,
                 List<Genre> genres, String movieCertificate) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.language = language;
        this.movieImageURL = movieImageURL;
        this.backgroundImageURL = backgroundImageURL;
        this.releaseDate = releaseDate;
        this.movieDuration = movieDuration;
        this.movieCertificate = movieCertificate;
        this.genres = genres;
    }

    // Add default constructor required by JPA
    public Movie() {
    }
}
