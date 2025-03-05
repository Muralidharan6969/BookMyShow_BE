package com.example.bookmyshow_be.Security;

import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Models.Admin;
import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Services.AdminService;
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

public class AdminTokenValidationFilter extends OncePerRequestFilter {
    private AdminService adminService;
    private JWTUtils jwtUtils;

    public AdminTokenValidationFilter(JWTUtils jwtUtils, AdminService adminService){
        this.jwtUtils = jwtUtils;
        this.adminService = adminService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("path - " + path);
        if (!path.startsWith("/admin/")) {
            System.out.println("Skipping token validation for non-admin paths");
            filterChain.doFilter(request, response); // Skip processing for non-admin paths
            return;
        }

        if (path.contains("/register") || path.contains("/login")) {
            System.out.println("Skipping token validation for admin registration");
            filterChain.doFilter(request, response); // Skip token validation for admin registration
            return;
        }
        System.out.println("Not skipping token validation for admin registration");

        String token = request.getHeader("bms-admin-token");
        if (token != null){
            try {
                String adminId = jwtUtils.extractEntityId(token);
                Admin admin = adminService.getAdminByAdminId(Long.parseLong(adminId)); // Fetch user

                if (admin != null) {
                    request.setAttribute("authenticatedAdmin", admin); // Attach outlet details to request
                } else {
                    throw new UserNotFoundException("Admin details not found");
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
