package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Configuration for repository layer of application .
 */
@Configuration
@ComponentScan("com.epam.esm")
public class DaoConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Profile("!test")
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @Profile("prod")
    public HikariConfig hikariConfigProd() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("prodHikariCP");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("rMB232:P");
        hikariConfig.setSchema("student");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/gift_certificates");
        return hikariConfig;
    }

    @Bean
    @Profile("dev")
    public HikariConfig hikariConfigDev(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("devHikariCP");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("rMB232:P");
        hikariConfig.setSchema("student");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/gift_certificates_dev");
        return hikariConfig;
    }
}
