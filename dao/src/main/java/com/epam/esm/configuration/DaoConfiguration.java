package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
public class DaoConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Value("hikariCP")
    private String poolName;

    @Value("postgres")
    private String userName;

    @Value("rMB232:P")
    private String password;

    @Value("student")
    private String schema;

    @Value("org.postgresql.Driver")
    private String driverClassName;

    @Value("jdbc:postgresql://localhost:5432/gift_certificates")
    private String jdbcUrl;

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName(poolName);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setSchema(schema);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        return hikariConfig;
    }
}
