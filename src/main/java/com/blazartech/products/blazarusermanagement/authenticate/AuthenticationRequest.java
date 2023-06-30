/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.authenticate;

import java.io.Serializable;

/**
 *
 * @author AAR1069
 */
public record AuthenticationRequest(String username, String password) implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    
}
