package com.example.bookmyshow_be.Security;

import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Services.OutletService;
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
public class OutletTokenValidationFilter extends OncePerRequestFilter {
    private OutletService outletService;
    private JWTUtils jwtUtils;

    @Autowired
    public OutletTokenValidationFilter(JWTUtils jwtUtils, OutletService outletService){
        this.jwtUtils = jwtUtils;
        this.outletService = outletService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("bms-outlet-token");
        if (token != null){
            try {
                String outletId = jwtUtils.extractEntityId(token);
                Outlet outlet = outletService.getOutletByOutletId(Long.parseLong(outletId)); // Fetch user

                if (outlet != null) {
                    request.setAttribute("authenticatedOutlet", outlet); // Attach outlet details to request
                } else {
                    throw new UserNotFoundException("Outlet details not found");
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
