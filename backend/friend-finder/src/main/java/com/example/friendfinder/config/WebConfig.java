package com.example.friendfinder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
String uploadDirectory="src/main/resources/static/assets/uploads/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDirectory + "/");

        // If you're still using /assets/uploads/ path
        registry.addResourceHandler("/assets/uploads/**")
                .addResourceLocations("file:" + uploadDirectory + "/");
    }
}
