/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarusermanagement.config;

import com.blazartech.products.crypto.BlazarCryptoFile;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author AAR1069
 */
@Configuration
public class DataSourceConfiguration {
    
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);
    
    @Autowired
    private BlazarCryptoFile cryptoFile;

    public DataSource buildDataSource(String userID, String resourceID, String driverClass, String url, int poolSize) {        
        logger.info("building data source for " + url + " via dbcp");
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(userID);
        config.setPassword(cryptoFile.getPassword(userID, resourceID));
        config.setDriverClassName(driverClass);
        config.setMaximumPoolSize(poolSize);
        config.setPoolName("user-management-pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        DataSource ds = new HikariDataSource(config);
        
        return ds;

    }

    @Value("${database.url}")
    private String url;
    
    @Value("${database.userID}")
    private String userID;
    
    @Value("${database.resourceID}")
    private String resourceID;
    
    @Value("${database.driverClass}")
    private String driverClass;
    
    @Bean
    public DataSource dataSource() {
        return buildDataSource(userID, resourceID, driverClass, url, 10);
    }
}
