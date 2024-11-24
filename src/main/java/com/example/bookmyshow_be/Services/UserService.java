package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Models.UserTokenPayload;
import com.example.bookmyshow_be.Repositories.UserRepository;
import com.example.bookmyshow_be.Utils.ENUMS.RoleEnums;
import com.example.bookmyshow_be.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTUtils jwtUtils;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       JWTUtils jwtUtils){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public User userSignUp(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String userLogin(String email, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }

        if(!bCryptPasswordEncoder.matches(password, userOptional.get().getPassword())){
            throw new RuntimeException("Password is incorrect");
        }

        User user = userOptional.get();

        return jwtUtils.generateUserToken(new UserTokenPayload(user.getUserId(),
                user.getName(), RoleEnums.USER.toString()));
    }

    public User getUserByUserId(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            return null;
        }
        return userOptional.get();
    }
}
