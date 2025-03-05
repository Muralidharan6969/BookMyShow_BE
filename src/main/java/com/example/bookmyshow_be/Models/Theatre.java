package com.example.bookmyshow_be.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "theatres")
public class Theatre extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="theatreId")
    private Long theatreId;

    @Column(name ="theatreName", nullable = false)
    private String theatreName;

    @Column(name ="theatreAddress", nullable = false)
    private String theatreAddress;

    @ManyToOne
    private Outlet outlet;

    @ManyToOne
    private City city;

    @Column(name = "isAdminApproved", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAdminApproved = false;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    private List<Screen> screens;
}
