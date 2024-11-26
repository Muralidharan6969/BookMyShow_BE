package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.OutletDTOs.OutletLoginRequestDTO;
import com.example.bookmyshow_be.DTOs.OutletDTOs.OutletLoginResponseDTO;
import com.example.bookmyshow_be.DTOs.OutletDTOs.OutletRegisterRequestDTO;
import com.example.bookmyshow_be.DTOs.OutletDTOs.OutletRegisterResponseDTO;
import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Services.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outlet")
public class OutletController {
    private OutletService outletService;

    @Autowired
    public OutletController(OutletService outletService){
        this.outletService = outletService;
    }

    @PostMapping("/register")
    public OutletRegisterResponseDTO registerOutlet(@RequestBody OutletRegisterRequestDTO requestDTO){
        return OutletRegisterResponseDTO.convertOutletToDTO
                (outletService.registerOutlet(OutletRegisterRequestDTO.convertDTOToOutlet((requestDTO))));
    }

    @PostMapping("/login")
    public OutletLoginResponseDTO outletLogin(@RequestBody OutletLoginRequestDTO requestDTO) throws UserNotFoundException {
        return OutletLoginResponseDTO.convertTokenStringToDTO
                (outletService.outletLogin(requestDTO.getEmail(), requestDTO.getPassword()));
    }
}
