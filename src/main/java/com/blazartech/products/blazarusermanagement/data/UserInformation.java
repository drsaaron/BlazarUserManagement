/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.data;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author AAR1069
 */
public record UserInformation(String userID, String displayName, Collection<String> roles, String password) implements Serializable {
    
    
}
