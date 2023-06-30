/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.security;

import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserDataEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserDataEntityRepository;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserRoleEntityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * provide a user details service that uses the app to authenticate itself.
 *
 * @author AAR1069
 */
@Component
public class UserManagementUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementUserDetailsService.class);

    @Autowired
    private UserDataEntityRepository userRepository;

    @Autowired
    private UserRoleEntityRepository userRoleRepository;

    /**
     * get user details by userID
     * 
     * @param userID the user ID
     * @return user information, of type @see(BlazarSecurityUser)
     * @throws UsernameNotFoundException 
     */
    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        logger.info("loading user " + userID);

        UserDataEntity userDataEntity = userRepository.findByUserId(userID);
        if (userDataEntity == null) {
            throw new UsernameNotFoundException(userID);
        }

        // for some reason userDataEntity.getUserRoleEntityCollection() returns null
        // so read directly
        Collection<UserRoleEntity> roles = userRoleRepository.findByUserNum(userDataEntity.getUserNum());

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            roles.stream()
                    .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getUserRoleTypeCde().getUserRoleTypeTxt()))
                    .forEachOrdered(authorities::add);
        }
        
        BlazarSecurityUser user = new BlazarSecurityUser(userDataEntity.getUserId(), userDataEntity.getUserPwd(), userDataEntity.getDisplayName(), authorities);
        logger.info("got user " + user);
                
        return user;
    }

}
