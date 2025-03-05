package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.Services.ScreenService;
import com.example.bookmyshow_be.Services.SeatService;
import com.example.bookmyshow_be.Services.ShowService;
import com.example.bookmyshow_be.Services.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shows")
public class ShowController {


    private ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/{showId}/details")
    public ResponseEntity<?> getShowDetails(@PathVariable Long showId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseWrapper("Show details fetched successfully!",
                        showService.getShowDetails(showId)));
    }
}
