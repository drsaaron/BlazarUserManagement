/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data.access.impl;

import com.blazartech.products.blazarusermanagement.data.UserInformation;
import com.blazartech.products.blazarusermanagement.data.access.UserManagementDataAccess;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserDataEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleEntity;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.UserRoleTypeValEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserDataEntityRepository;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserRoleEntityRepository;
import com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos.UserRoleTypeValEntityRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author AAR1069
 */
@Component
public class UserManagementDataAccessImpl implements UserManagementDataAccess {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementDataAccessImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDataEntityRepository userRepository;

    @Autowired
    private UserRoleTypeValEntityRepository roleRepository;

    @Autowired
    private UserRoleEntityRepository userRoleRepository;

    @Override
    public UserInformation getUserInformation(String userID) {
        logger.info("getting user " + userID);

        UserDataEntity ude = userRepository.findByUserId(userID);
        return buildUser(ude);
    }

    @Override
    public UserInformation addUser(String userID, String displayName, String password) {
        logger.info("adding user " + userID);

        UserDataEntity ue = new UserDataEntity();
        ue.setDisplayName(displayName);
        ue.setUserId(userID);
        ue.setUserPwd(passwordEncoder.encode(password));

        userRepository.save(ue);

        UserInformation ui = buildUser(ue);

        return ui;
    }

    private UserInformation buildUser(UserDataEntity ue) {
        if (ue != null) {
            List<String> roles = new ArrayList<>();

            if (ue.getUserRoleEntityCollection() != null) {
                ue.getUserRoleEntityCollection().stream()
                        .map(r -> r.getUserRoleTypeCde().getUserRoleTypeTxt())
                        .forEach(roles::add);
            }

            UserInformation ui = new UserInformation(ue.getUserId(), ue.getDisplayName(), roles, "<PROTECTED>");

            return ui;
        } else {
            return null;
        }
    }

    @Override
    public Collection<String> getRoles() {
        logger.info("getting list of roles");

        Collection<UserRoleTypeValEntity> roleData = roleRepository.findAll();
        List<String> roles = roleData.stream()
                .map(r -> r.getUserRoleTypeTxt())
                .collect(Collectors.toList());

        return roles;
    }

    @Override
    public UserInformation addRoleToUser(String userID, String newRole) {
        logger.info("updating roles for user " + userID);

        // get the current user.
        UserDataEntity user = userRepository.findByUserId(userID);

        // is the role already there?
        Optional<UserRoleEntity> foundRole = user.getUserRoleEntityCollection().stream()
                .filter(r -> r.getUserRoleTypeCde().getUserRoleTypeTxt().equals(newRole))
                .findFirst();
        if (foundRole.isPresent()) {
            // already there.
            logger.info("role already there.");
            return buildUser(user);
        }

        // not there so add.
        UserRoleTypeValEntity roleType = roleRepository.findByUserRoleTypeTxt(newRole);
        UserRoleEntity ue = new UserRoleEntity();
        ue.setUserNum(user);
        ue.setUserRoleTypeCde(roleType);

        userRoleRepository.save(ue);

        // re-retrieve the user to get the update
        user = userRepository.findByUserId(userID);

        return buildUser(user);
    }

}
