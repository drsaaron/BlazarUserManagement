/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.products.blazarusermanagement;

import com.blazartech.products.blazarusermanagement.data.UserInformation;
import com.blazartech.products.blazarusermanagement.data.access.UserManagementDataAccess;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * an initialization step to create a default admin user for the application.
 * One cannot add a user via the services until a user has been added, so do so.
 * @author scott
 */
@Component
public class LoadAdminUser implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadAdminUser.class);
    
    @Autowired
    private UserManagementDataAccess dataAccess;

    private static final String ADMIN_USER_ID = "blazaradmin";
    private static final String ADMIN_DEFAULT_PASSWD = "blazaradminpw";
    private static final String ADMIN_ROLE = "BLAZAR_USER_MGMT_ADMIN";
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserInformation user = dataAccess.getUserInformation(ADMIN_USER_ID);
        if (user == null) {
            logger.info("adding default admin user {}.  Please update password", ADMIN_USER_ID);
            dataAccess.addUser(ADMIN_USER_ID, "System admin", ADMIN_DEFAULT_PASSWD);
            dataAccess.addRoleToUser(ADMIN_USER_ID, ADMIN_ROLE);
        } else {
            logger.info("default admin ID {} already present", ADMIN_USER_ID);
        }
    }

}
