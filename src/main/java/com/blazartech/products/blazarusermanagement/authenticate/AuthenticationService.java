/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.authenticate;

import com.blazartech.products.blazarusermanagement.security.BlazarSecurityUser;
import com.blazartech.products.blazarusermanagement.tokenutil.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * this REST service will authenticate a user and return a JWT token.
 * 
 * @author AAR1069
 */
@RestController
@CrossOrigin
public class AuthenticationService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @Operation(summary = "authenticate a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "the authenticated user data",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))})
    })
    public ResponseEntity<?> createAuthenticationToken(@Parameter(description = "user request data") @RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.username(), authenticationRequest.password());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.username());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token, new AuthenticatedUser(userDetails.getUsername(), ((BlazarSecurityUser) userDetails).getDisplayName())));
    }

    private void authenticate(String username, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }
}
