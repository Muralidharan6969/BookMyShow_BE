package com.example.bookmyshow_be.Security;

import com.example.bookmyshow_be.Models.Admin;
import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Services.AdminService;
import com.example.bookmyshow_be.Services.OutletService;
import com.example.bookmyshow_be.Services.UserService;
import com.example.bookmyshow_be.Utils.ENUMS.RoleEnums;
import com.example.bookmyshow_be.Utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenValidationFilter extends OncePerRequestFilter {
    private JWTUtils jwtUtils;
    private AdminService adminService;
    private UserService userService;
    private OutletService outletService;

    public TokenValidationFilter(JWTUtils jwtUtils, AdminService adminService, UserService userService, OutletService outletService){
        this.jwtUtils = jwtUtils;
        this.adminService = adminService;
        this.userService = userService;
        this.outletService = outletService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String token = request.getHeader("bms-auth-token");

        if (isAuthenticationEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if(token != null){
            try{
                String role = jwtUtils.extractEntityRole(token);
                String entityId = jwtUtils.extractEntityId(token);

                if (role != null && entityId != null) {
                    request.setAttribute("userType", role);
                    RoleEnums roleEnum = RoleEnums.valueOf(role.toUpperCase());

                    switch (roleEnum) {
                        case ADMIN -> {
                            Admin admin = adminService.getAdminByAdminId(Long.parseLong(entityId));
                            if (admin != null) {
                                request.setAttribute("authenticatedAdmin", admin);
                            }
                        }
                        case OUTLET -> {
                            Outlet outlet = outletService.getOutletByOutletId(Long.parseLong(entityId));
                            if (outlet != null) {
                                request.setAttribute("authenticatedOutlet", outlet);
                            }
                        }
                        case USER -> {
                            User user = userService.getUserByUserId(Long.parseLong(entityId));
                            if (user != null) {
                                request.setAttribute("authenticatedUser", user);
                            }
                        }
                        default -> throw new IllegalArgumentException("Unknown role: " + role);
                    }
                }
            } catch(Exception e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Token");
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticationEndpoint(String path) {
        return path.contains("/login") || path.contains("/register") || path.contains("/signup");
    }
}
