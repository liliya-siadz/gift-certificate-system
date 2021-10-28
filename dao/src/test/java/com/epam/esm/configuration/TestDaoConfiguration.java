package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
@Profile("test")
public class TestDaoConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(EmbeddedDatabase embeddedDatabase) {
        return new JdbcTemplate(embeddedDatabase);
    }

    @Bean
    PlatformTransactionManager transactionManager(EmbeddedDatabase embeddedDatabase) {
        return new DataSourceTransactionManager(embeddedDatabase);
    }

    @Bean
    public EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .setName("gift_certificates_test")
                .ignoreFailedDrops(true)
                .addScript("classpath:gcs_ddl.sql")
                .addScript("classpath:gcs_dml.sql")
                .build();
    }
}
