package com.example.bookmyshow_be.Controllers;

import com.example.bookmyshow_be.DTOs.ResponseWrapper;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserLoginRequestDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserLoginResponseDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserSignUpRequestDTO;
import com.example.bookmyshow_be.DTOs.UserDTOs.UserSignUpResponseDTO;
import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> signup(@RequestBody UserSignUpRequestDTO requestDTO){
        System.out.println("Executing User Signup controller");
        UserSignUpResponseDTO responseDTO =  UserSignUpResponseDTO.convertUserToDTO(userService.userSignUp
                (UserSignUpRequestDTO.convertDTOToUser(requestDTO)));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User registered successfully. Kindly Login to continue", responseDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO requestDTO) throws UserNotFoundException {
        UserLoginResponseDTO responseDTO = userService.userLogin(requestDTO.getEmail(), requestDTO.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseWrapper("User logged in successfully!", responseDTO));
    }
}
