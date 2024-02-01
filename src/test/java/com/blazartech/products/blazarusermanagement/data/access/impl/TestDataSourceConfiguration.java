/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.blazartech.products.blazarusermanagement.data.access.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author aar1069
 */
@Configuration
@PropertySource("classpath:unittest.properties")
public class TestDataSourceConfiguration {
    
    @Value("${test.db.user}")
    private String user;

    @Value("${test.db.driverClass}")
    private String driverClass;

    @Value("${test.db.url}")
    private String url;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword("blah");
        config.setDriverClassName(driverClass);
        config.setMaximumPoolSize(5);
        
        DataSource ds = new HikariDataSource(config);
        
        return ds;
    }

}
