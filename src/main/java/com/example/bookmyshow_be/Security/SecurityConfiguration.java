package com.example.bookmyshow_be.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain userSecurityFilterChain
            (HttpSecurity http, UserTokenValidationFilter userTokenValidationFilter) throws Exception{
        return http
                .securityMatcher("/user/**")
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/user/login", "/user/signup").permitAll()
                    .anyRequest().authenticated()
                .and()
                .addFilterBefore(userTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain outletSecurityFilterChain
            (HttpSecurity http, OutletTokenValidationFilter outletTokenValidationFilter) throws Exception {
        return http
                .securityMatcher("/outlet/**")
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/outlet/login", "/outlet/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(outletTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain
            (HttpSecurity http, AdminTokenValidationFilter adminTokenValidationFilter) throws Exception {
        return http
                .securityMatcher("/admin/**")
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/admin/login", "/admin/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(adminTokenValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
