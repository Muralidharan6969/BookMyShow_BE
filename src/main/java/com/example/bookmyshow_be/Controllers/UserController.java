package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.UserDTOs.UserLoginRequestDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserLoginResponseDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserSignUpRequestDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserSignUpResponseDTO;
import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserSignUpResponseDTO signup(@RequestBody UserSignUpRequestDTO requestDTO){
        System.out.println("Executing User Signup controller");
        return UserSignUpResponseDTO.convertUserToDTO(userService.userSignUp
                (UserSignUpRequestDTO.convertDTOToUser(requestDTO)));
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginRequestDTO requestDTO) throws UserNotFoundException {
        return UserLoginResponseDTO.convertTokenStringToDTO
                (userService.userLogin(requestDTO.getEmail(), requestDTO.getPassword()));
    }
}
