package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="cityId")
    private Long cityId;

    @Column(name ="cityName", nullable = false)
    private String cityName;
}
