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

    // Security Filter Chain for User-specific routes
//    @Bean
//    public SecurityFilterChain userSecurityFilterChain
//            (HttpSecurity http) throws Exception{
//        UserTokenValidationFilter userTokenValidationFilter =
//                new UserTokenValidationFilter(jwtUtils, userService);
//        return http
//                .securityMatcher("/users/**")
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .requestMatchers("/users/login", "/users/signup").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .addFilterBefore(userTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    // Security Filter Chain for Outlet-specific routes
//    @Bean
//    public SecurityFilterChain outletSecurityFilterChain
//            (HttpSecurity http) throws Exception {
//        OutletTokenValidationFilter outletTokenValidationFilter =
//                new OutletTokenValidationFilter(jwtUtils, outletService);
//        return http
//                .securityMatcher("/outlet/**")
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .requestMatchers("/outlet/login", "/outlet/register").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .addFilterBefore(outletTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }

    // Security Filter Chain for Admin-specific routes
//    @Bean
//    public SecurityFilterChain adminSecurityFilterChain
//            (HttpSecurity http) throws Exception {
//        AdminTokenValidationFilter adminTokenValidationFilter =
//                new AdminTokenValidationFilter(jwtUtils, adminService);
//        return http
//                .securityMatcher("/admin/**")
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .requestMatchers("/admin/login", "/admin/register").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .addFilterBefore(adminTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
}
