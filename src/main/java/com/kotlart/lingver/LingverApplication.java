package com.kotlart.lingver;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LingverApplication implements WebMvcConfigurer {
    private final static String UI_URL = "http://localhost:3000";// TODO move to configuration
    public static void main(String[] args) {
        SpringApplication.run(LingverApplication.class, args);
    }

    /*  @Override
      public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**").allowedOrigins(UI_URL).exposedHeaders("Access-Control-Allow-Credentials");
      }
  */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

