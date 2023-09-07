/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.products.blazarusermanagement.data.access.impl;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 *
 * @author aar1069
 */
@Configuration
@EnableJpaRepositories(
        basePackages = {
            "com.blazartech.products.blazarusermanagement.data.access.impl.jpa.repos"
        },
        entityManagerFactoryRef = "testEntityManager",
        transactionManagerRef = "testTransactionManager"
)
public class TestEntityManagerConfiguration {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Bean
    public LocalContainerEntityManagerFactoryBean testEntityManager() {
        LocalContainerEntityManagerFactoryBean f = new LocalContainerEntityManagerFactoryBean();
        f.setDataSource(dataSource);
        f.setPersistenceXmlLocation("classpath:META-INF/test-persistence.xml");
        f.setJpaVendorAdapter(jpaVendorAdapter);
        f.setPersistenceUnitName("com.blazartech_BlazarUserManagement_test_jar_1.0-SNAPSHOTPU");

        return f;
    }

}
