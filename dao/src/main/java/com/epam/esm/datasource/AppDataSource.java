package com.epam.esm.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm")
public class AppDataSource {
    @Bean
    @Primary
    @Profile("prod")
    @ConfigurationProperties("app.datasource.prod")
    public DataSourceProperties prodDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @Profile("dev")
    @ConfigurationProperties("app.datasource.dev")
    public DataSourceProperties devDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.configuration")
    public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
