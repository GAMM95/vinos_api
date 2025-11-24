package com.gamm.vinos_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/FotosUsuarios/**")
        .addResourceLocations("file:D:/FotosUsuarios/")
        .setCachePeriod(0);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/FotosUsuarios/**")
        .allowedOrigins(
            "http://localhost:4200",
            "http://26.118.48.22:4200"
        )
        .allowedMethods("GET")
        .allowedHeaders("*")
        .allowCredentials(true);
  }
}
