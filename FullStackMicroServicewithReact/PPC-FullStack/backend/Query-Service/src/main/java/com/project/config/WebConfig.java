package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // Allow requests from your front-end application
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")  // Front-end URL
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(false)  // Allows cookies and other credentials to be sent
//                .maxAge(3600);  // Cache preflight response for 1 hour
//    }
}
