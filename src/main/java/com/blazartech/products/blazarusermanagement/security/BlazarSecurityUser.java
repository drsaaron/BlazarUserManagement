/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * extend the standard User class to include the display name.
 * 
 * @author AAR1069
 */
public class BlazarSecurityUser extends User {

    private String displayName;

    public BlazarSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    public BlazarSecurityUser(String username, String password, String displayName, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
