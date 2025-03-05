package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminLoginRequestDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminLoginResponseDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterRequestDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterResponseDTO;
import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequestDTO requestDTO){
        AdminRegisterResponseDTO responseDTO = AdminRegisterResponseDTO.convertAdminToDTO
                (adminService.registerAdmin(AdminRegisterRequestDTO.convertDTOToAdmin(requestDTO)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Admin registered successfully. Kindly Login to continue", responseDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody AdminLoginRequestDTO requestDTO) throws UserNotFoundException {
        AdminLoginResponseDTO responseDTO = AdminLoginResponseDTO.convertTokenStringToDTO
                (adminService.adminLogin(requestDTO.getMobileNumber(), requestDTO.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Admin logged in successfully", responseDTO));
    }
}
