package com.learn.springrest.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring-REST API")
                        .description("Spring Boot REST API with OpenAPI documentation")
                        .version("v1.0.0"));
    }
}
