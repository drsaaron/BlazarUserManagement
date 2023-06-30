/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.security;

import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserDataEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleTypeValEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserDataEntityRepository;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserRoleEntityRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author AAR1069
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    UserManagementUserDetailsServiceTest.UserManagementUserDetailsServiceTestConfiguration.class
})
public class UserManagementUserDetailsServiceTest {
    
    private static final Logger logger = LoggerFactory.getLogger(UserManagementUserDetailsServiceTest.class);
    
    @Configuration
    static class UserManagementUserDetailsServiceTestConfiguration {
        
        @Bean
        public UserManagementUserDetailsService instance() {
            return new UserManagementUserDetailsService();
        }
    }
    
    @Autowired
    private UserManagementUserDetailsService instance;
    
    @MockBean
    private UserDataEntityRepository userRepository;
    
    @MockBean
    private UserRoleEntityRepository roleRepository;
    
    private static final String TEST_USER = "myUser";
    private static final String TEST_USER_DISPLAY_NAME = "My name is User";
    
    private static final UserRoleTypeValEntity TYPE_CODE_1 = new UserRoleTypeValEntity((short) 1, "Role1");
    private static final UserRoleTypeValEntity TYPE_CODE_2 = new UserRoleTypeValEntity((short) 2, "Role2");
    
    private static final UserDataEntity TEST_USER_ENTITY = new UserDataEntity(1L, TEST_USER, TEST_USER_DISPLAY_NAME, "abcd", new Date(), new Date());
    private static final List<UserRoleEntity> TEST_USER_ROLE_ENTITIES = List.of(new UserRoleEntity(1L, TYPE_CODE_1), new UserRoleEntity(2L, TYPE_CODE_2));
    
    private static final String INVALID_TEST_USER = "myBadUser";
    
    public UserManagementUserDetailsServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        // setup a found user
        Mockito.when(userRepository.findByUserId(TEST_USER))
                .thenReturn(TEST_USER_ENTITY);
        
        Mockito.when(roleRepository.findByUserNum(1L))
                .thenReturn(TEST_USER_ROLE_ENTITIES);
        
        // this user isn't found so return a null
        Mockito.when(userRepository.findByUserId(INVALID_TEST_USER))
                .thenReturn(null);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of loadUserByUsername method, of class UserManagementUserDetailsService.
     */
    @DisplayName("load a user from the repo")
    @Test
    public void testLoadUserByUsername() {
        logger.info("loadUserByUsername");
        
        String userID = TEST_USER;

        UserDetails result = instance.loadUserByUsername(userID);
        
        assertNotNull(result);
        assertTrue(result instanceof BlazarSecurityUser);
        assertEquals(TEST_USER, result.getUsername());
        assertEquals(TEST_USER_DISPLAY_NAME, ((BlazarSecurityUser) result).getDisplayName());
        assertEquals(TEST_USER_ROLE_ENTITIES.size(), result.getAuthorities().size());
        
        List<GrantedAuthority> grantedAuthoriesList = new ArrayList<>();
        grantedAuthoriesList.addAll(result.getAuthorities());
        
        for (int i = 0; i < TEST_USER_ROLE_ENTITIES.size(); i++) {
            UserRoleEntity e = TEST_USER_ROLE_ENTITIES.get(i);
            GrantedAuthority a = grantedAuthoriesList.get(i);
            
            assertEquals("ROLE_" + e.getUserRoleTypeCde().getUserRoleTypeTxt(), a.getAuthority());
        }
    }
    
    /**
     * unit test a user that's not found.
     */
    @Test
    public void testLoadUserByUsername_invalid() {
        logger.info("testLoadUserByUsername_invalid");
        
        String userID = INVALID_TEST_USER;    
        
        // this user isn't found, so loading should trigger an exception
        assertThrows(UsernameNotFoundException.class, () -> {
            instance.loadUserByUsername(userID);
        });
    }
    
}
