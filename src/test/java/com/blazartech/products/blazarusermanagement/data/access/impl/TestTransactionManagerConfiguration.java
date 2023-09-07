/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.products.blazarusermanagement.data.access.impl;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author aar1069
 */
@Configuration
public class TestTransactionManagerConfiguration {
    
    @Autowired
    private EntityManagerFactory entityManager;
    
    @Bean
    public PlatformTransactionManager testTransactionManager() {
        
        JpaTransactionManager tm = new JpaTransactionManager(entityManager);
        return tm;
    }

}
