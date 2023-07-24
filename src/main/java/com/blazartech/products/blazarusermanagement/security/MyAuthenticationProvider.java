/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.products.blazarusermanagement.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author aar1069
 */
//@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("authenticating " + authentication.getName());
        
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        logger.info("name = " + name);
        logger.info("password = " + password);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        logger.info("details password = " + userDetails.getPassword());
        
        String encodedPassword = passwordEncoder.encode(password);
        logger.info("encoded password = " + encodedPassword);
        
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.info("all good");
            Authentication a = new UsernamePasswordAuthenticationToken(name, authentication.getCredentials(), userDetails.getAuthorities());
            a.getAuthorities().forEach(auth -> logger.info("authority " + auth));
            logger.info("a.isAuthenticated = " + a.isAuthenticated());
            return a;
        } else {
            logger.info("not authorizaed");
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
