/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author AAR1069
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    /*  @Autowired
    private AuthenticationProvider myAuthenticationProvider; */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return auth.build();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        AuthenticationEntryPoint aep = (request, response, authException) -> { response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); };
        http
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers(request -> request.getRequestURI().startsWith("/authenticate")).permitAll()
                                .requestMatchers(request -> request.getRequestURI().startsWith("/api/v1")).authenticated()
                                .requestMatchers(request -> request.getRequestURI().startsWith("/monitoring")).permitAll()
                                .requestMatchers(request -> request.getRequestURI().startsWith("/v3/api-docs")).permitAll()
                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                )
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(f -> f.disable())
                .httpBasic(b -> b.authenticationEntryPoint(aep));
        return http.build();

    }

}
