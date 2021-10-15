package com.epam.esm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.epam.esm.mapper",
        "com.epam.esm.service"}
)
public class ServiceConfiguration {

}
