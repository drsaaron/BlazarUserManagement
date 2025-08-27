/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author AAR1069
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan("com.blazartech")
public class Main {
    
    public static void main(String... args) {
        SpringApplication.run(Main.class, args);
    }
}
