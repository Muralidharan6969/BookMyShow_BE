package com.example.bookmyshow_be.Security;

import com.example.bookmyshow_be.Services.AdminService;
import com.example.bookmyshow_be.Services.OutletService;
import com.example.bookmyshow_be.Services.UserService;
import com.example.bookmyshow_be.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final OutletService outletService;
    private final AdminService adminService;
    private final RBACConfig rbacConfig;

    @Autowired
    public SecurityConfiguration(JWTUtils jwtUtils, UserService userService,
                                 OutletService outletService, AdminService adminService,
                                 RBACConfig rbacConfig) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.outletService = outletService;
        this.adminService = adminService;
        this.rbacConfig = rbacConfig;
    }

    // Main Security Filter Chain for fallback routes
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .addFilterBefore(
                        new TokenValidationFilter(jwtUtils, adminService, userService, outletService),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAfter(
                        new RBACFilter(rbacConfig),
                        TokenValidationFilter.class
                )
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .build();
    }
}
