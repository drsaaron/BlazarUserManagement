/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data.access;

import com.blazartech.products.blazarusermanagement.data.UserInformation;
import java.util.Collection;

/**
 *
 * @author AAR1069
 */
public interface UserManagementDataAccess {
    
    public UserInformation getUserInformation(String userID);
    
    public UserInformation addUser(String userID, String displayName, String password);
    
    public Collection<String> getRoles();
    
    public UserInformation addRoleToUser(String userID, String newRole);
}
