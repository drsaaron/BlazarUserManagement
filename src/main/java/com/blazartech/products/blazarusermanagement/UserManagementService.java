/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement;

import com.blazartech.products.blazarusermanagement.data.UserInformation;
import com.blazartech.products.blazarusermanagement.data.access.UserManagementDataAccess;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AAR1069
 */
@RestController
@RequestMapping(value = "/api/v1")
@OpenAPIDefinition(info = @Info(
        title = "User management service",
        version = "1.0"
))
public class UserManagementService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);
    
    @Autowired
    private UserManagementDataAccess dataAccess;
    
    @RequestMapping(value = "/user/{userID}", method = RequestMethod.GET) 
    @PreAuthorize("hasAuthority('ROLE_BLAZAR_USER_MGMT_ADMIN') or hasAuthority('ROLE_BLAZAR_USER_CLIENT_APP')")
    @Operation(summary = "get details of a given user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "user data",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInformation.class))})
    })
    public UserInformation getUser(@Parameter(description = "the user ID") @PathVariable String userID) {
        logger.info("getting user " + userID);
        return dataAccess.getUserInformation(userID);
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ROLE_BLAZAR_USER_MGMT_ADMIN')")
    @Operation(summary = "create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "user data",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInformation.class))})
    })
    public UserInformation addUser(@Parameter(description = "the new user") @RequestBody UserInformation userInfo) {
        logger.info("adding user " + userInfo.userID());
        
        userInfo = dataAccess.addUser(userInfo.userID(), userInfo.displayName(), userInfo.password());
        
        return userInfo;
    }
    
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_BLAZAR_USER_MGMT_ADMIN')")
    @Operation(summary = "get list of user roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "roles",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String[].class))})
    })
    public Collection<String> getRoles() {
        return dataAccess.getRoles();
    }
    
    @RequestMapping(value = "/user/{userID}", method = RequestMethod.POST, params = { "role" })
    @PreAuthorize("hasAuthority('ROLE_BLAZAR_USER_MGMT_ADMIN')")
    @Operation(summary = "add a role for a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "roles",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserInformation.class))})
    })
    public UserInformation addRole(
            @Parameter(description = "the user ID") @PathVariable String userID, 
            @Parameter(description = "the role being added") @RequestParam(required = true) String role) {
        logger.info("adding role " + role + " to user " + userID);
        
        return dataAccess.addRoleToUser(userID, role);
    }
}
