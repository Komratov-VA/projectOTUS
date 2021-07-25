package com.otus.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class DBConfig {

    @Value("${database.url}")
    String url;
//    @Value("${database.urlSlave}")
//    String urlSlave;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "database")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Primary
//    @Bean(name = "dataSource")
//    @ConfigurationProperties(prefix = "database")
//    public DataSource dataSourceSlave() {
//        return DataSourceBuilder.create().url(urlSlave).build();
//    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

//    @Bean
//    public JdbcTemplate jdbcTemplateSlave() {
//        return new JdbcTemplate(dataSourceSlave());
//    }
}