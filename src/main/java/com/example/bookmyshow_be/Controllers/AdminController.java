package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminLoginRequestDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminLoginResponseDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterRequestDTO;
import com.example.bookmyshow_be.DTOs.AdminDTOs.AdminRegisterResponseDTO;
import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AdminRegisterResponseDTO registerAdmin(@RequestBody AdminRegisterRequestDTO requestDTO){
        return AdminRegisterResponseDTO.convertAdminToDTO
                (adminService.registerAdmin(AdminRegisterRequestDTO.convertDTOToAdmin(requestDTO)));
    }

    @PostMapping("/login")
    public AdminLoginResponseDTO adminLogin(@RequestBody AdminLoginRequestDTO requestDTO) throws UserNotFoundException {
        return AdminLoginResponseDTO.convertTokenStringToDTO
                (adminService.adminLogin(requestDTO.getMobileNumber(), requestDTO.getPassword()));
    }
}
