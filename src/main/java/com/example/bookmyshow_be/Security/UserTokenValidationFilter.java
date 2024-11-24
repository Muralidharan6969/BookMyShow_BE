package com.example.bookmyshow_be.Security;

import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Services.UserService;
import com.example.bookmyshow_be.Utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserTokenValidationFilter extends OncePerRequestFilter {
    private UserService userService;
    private JWTUtils jwtUtils;

    @Autowired
    public UserTokenValidationFilter(JWTUtils jwtUtils, UserService userService){
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("bms-user-token");
        if (token != null){
            try {
                String userId = jwtUtils.extractEntityId(token);
                User user = userService.getUserByUserId(Long.parseLong(userId)); // Fetch user

                if (user != null) {
                    request.setAttribute("authenticatedUser", user); // Attach user to request
                } else {
                    throw new UserNotFoundException("User not found");
                }
            } catch(Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Token");
                return;
            }
        } else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
