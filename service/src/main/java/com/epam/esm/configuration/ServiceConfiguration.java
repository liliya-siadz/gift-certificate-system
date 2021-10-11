package com.epam.esm.configuration;

import com.epam.esm.dao.TagDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.epam.esm.service",
        "com.epam.esm.configuration",
        "com.epam.esm.mapper.impl"}
)
public class ServiceConfiguration {
    @Bean
    public TagDao tagDao() {
        return new TagDao();
    }
}
