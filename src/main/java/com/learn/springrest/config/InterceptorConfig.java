package com.learn.springrest.config;

import com.learn.springrest.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;

    public InterceptorConfig(LoggingInterceptor loggingInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**") // Apply to all paths
                .excludePathPatterns("/api-docs/**", "/swagger-ui/**", "/api/auth"); // Exclude API docs and Swagger UI
    }
}
