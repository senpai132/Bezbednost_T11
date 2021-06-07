package com.example.bolnicaServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**").allowedMethods("POST").allowedOrigins("http://localhost:4200");
        registry.addMapping("/**").allowedOrigins("https://localhost:4201");
        registry.addMapping("/**").allowedOrigins("https://localhost:8080");
    }
}
