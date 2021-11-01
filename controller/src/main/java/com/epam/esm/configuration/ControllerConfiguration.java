package com.epam.esm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for controller layer of application.
 * Imports Spring MVC framework .
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class ControllerConfiguration implements WebMvcConfigurer {
}
