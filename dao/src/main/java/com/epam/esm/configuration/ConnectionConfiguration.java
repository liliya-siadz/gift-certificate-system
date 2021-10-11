package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = {
                "com.epam.esm.dao",
                "com.epam.esm.mapper",
                "com.epam.esm.configuration"
        }
)
public class ConnectionConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public HikariConfig hikariConfig() {
        String propertyFileName = "/hikari.properties";
        return new HikariConfig(propertyFileName);
    }

    @PreDestroy
    public void close() {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource();
        hikariDataSource.close();
    }
}
