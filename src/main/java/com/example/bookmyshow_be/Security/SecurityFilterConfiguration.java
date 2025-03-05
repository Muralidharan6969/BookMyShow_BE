package com.example.bookmyshow_be.Security;

import com.example.bookmyshow_be.Services.AdminService;
import com.example.bookmyshow_be.Services.OutletService;
import com.example.bookmyshow_be.Services.UserService;
import com.example.bookmyshow_be.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityFilterConfiguration {
    private UserService userService;
    private OutletService outletService;
    private AdminService adminService;
    private JWTUtils jwtUtils;

    @Autowired
    public SecurityFilterConfiguration(UserService userService, OutletService outletService,
                                       AdminService adminService, JWTUtils jwtUtils) {
        this.userService = userService;
        this.outletService = outletService;
        this.adminService = adminService;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public UserTokenValidationFilter userTokenValidationFilter() {
        return new UserTokenValidationFilter(jwtUtils, userService);
    }

    @Bean
    public OutletTokenValidationFilter outletTokenValidationFilter() {
        return new OutletTokenValidationFilter(jwtUtils, outletService);
    }

    @Bean
    public AdminTokenValidationFilter adminTokenValidationFilter() {
        return new AdminTokenValidationFilter(jwtUtils, adminService);
    }
}
