/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.blazartech.products.blazarusermanagement.data.access.impl;

import com.blazartech.products.blazarusermanagement.data.UserInformation;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.config.JpaVendorAdapterConfiguration;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.config.TransactionManagerConfiguration;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserRoleEntityRepository;
import jakarta.transaction.Transactional;
import java.util.Collection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author aar1069
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    UserManagementDataAccessImplTest.UserManagementDataAccessImplTestConfiguration.class,
    TestDataSourceConfiguration.class,
    TestEntityManagerConfiguration.class,
    JpaVendorAdapterConfiguration.class,
    TransactionManagerConfiguration.class
})
@Transactional
public class UserManagementDataAccessImplTest {
    
    private static final Logger logger = LoggerFactory.getLogger(UserManagementDataAccessImplTest.class);
    
    @Configuration
    @PropertySource("classpath:unittest.properties")
    public static class UserManagementDataAccessImplTestConfiguration {
        
        @Bean
        public UserManagementDataAccessImpl instance() {
            return new UserManagementDataAccessImpl();
        }
        
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new DummyPasswordEncoder();
        }
    }
    
    @Autowired
    private UserManagementDataAccessImpl instance;
    
    public UserManagementDataAccessImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    private static final String TEST_USER_1 = "TEST1";
    private static final String TEST_USER_1_DISPLAY = "I am a test";
    private static final Long TEST_USER_1_NUM = 1L;
    
    private static final String TEST_USER_2 = "TEST2";
    private static final String TEST_USER_2_DISPLAY = "I am another test";
    private static final Long TEST_USER_2_NUM = 2L;
    
    /**
     * Test of getUserInformation method, of class UserManagementDataAccessImpl.
     */
    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetUserInformation_basic() {
        logger.info("getUserInformation_basic");
        
        String userID = TEST_USER_1;

        UserInformation result = instance.getUserInformation(userID);
        
        assertNotNull(result);
        assertEquals(TEST_USER_1_DISPLAY, result.displayName());
        assertNotNull(result.roles());
        assertEquals(0, result.roles().size());
    }

    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetUserInformation_withroles() {
        logger.info("getUserInformation_withroles");
        
        String userID = TEST_USER_2;

        UserInformation result = instance.getUserInformation(userID);
        
        assertNotNull(result);
        assertEquals(TEST_USER_2_DISPLAY, result.displayName());
        assertNotNull(result.roles());
        assertEquals(1, result.roles().size());
    }
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testGetRoles() {
        logger.info("getRoles");
        
        Collection<String> result = instance.getRoles();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    
    private static final String ROLE_1 = "Role1";
    private static final String ROLE_2 = "Role2";
    
    @Autowired
    private UserRoleEntityRepository roleEntityRepository;
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testAddRoleToUser_norole() {
        logger.info("addRoleToUser_norole");
        
        String userID = TEST_USER_1;
        
        UserInformation userBefore = instance.getUserInformation(userID);
        assertEquals(0, userBefore.roles().size());
        
        instance.addRoleToUser(userID, ROLE_1);
        
        // role wasn't there before, so should be added.  Check directory via
        // the repository since re-reading the user doesn't seem to pick it up.
        Collection<UserRoleEntity> roles = roleEntityRepository.findByUserNum(TEST_USER_1_NUM);
        assertEquals(1, roles.size());
    }
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testAddRoleToUser_alreadyhasrole() {
        logger.info("addRoleToUser_alreadyhasrole");
        
        String userID = TEST_USER_2;
        
        UserInformation userBefore = instance.getUserInformation(userID);
        assertEquals(1, userBefore.roles().size());
        
        instance.addRoleToUser(userID, ROLE_2);
        
        // role was there before, so should not be added.  Check directory via
        // the repository since re-reading the user doesn't seem to pick it up.
        Collection<UserRoleEntity> roles = roleEntityRepository.findByUserNum(TEST_USER_2_NUM);
        assertEquals(1, roles.size());
    }
    
    @Test
    @Sql("classpath:dalTest.sql")
    public void testAddRoleToUser_newrole() {
        logger.info("addRoleToUser_newrole");
        
        String userID = TEST_USER_2;
        
        UserInformation userBefore = instance.getUserInformation(userID);
        assertEquals(1, userBefore.roles().size());
        
        instance.addRoleToUser(userID, ROLE_1);
        
        // new role should be added.  Check directory via
        // the repository since re-reading the user doesn't seem to pick it up.
        Collection<UserRoleEntity> roles = roleEntityRepository.findByUserNum(TEST_USER_2_NUM);
        assertEquals(2, roles.size());
    }
}
