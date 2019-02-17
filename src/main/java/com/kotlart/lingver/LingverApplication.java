package com.kotlart.lingver;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LingverApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(LingverApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "DELETE", "UPDATE", "OPTIONS"); //TODO move to configuration
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

