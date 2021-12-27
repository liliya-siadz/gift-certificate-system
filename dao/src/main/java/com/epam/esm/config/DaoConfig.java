package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration of repository layer for data sources,
 * Hibernate transaction manager, session factory,
 * specific Hibernate properties .
 */
@Configuration
@EnableTransactionManagement
public class DaoConfig {
    @Bean(name = "transactionManager")
    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean localSessionFactoryBean) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(localSessionFactoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.epam.esm.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.configuration")
    public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

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

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return hibernateProperties;
    }
}
