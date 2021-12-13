package com.example.scm_system.config;

import com.example.scm_system.web.interceptor.StatisticsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfiguration implements WebMvcConfigurer {

    private final StatisticsInterceptor statisticsInterceptor;

    public WebInterceptorConfiguration(StatisticsInterceptor statisticsInterceptor) {
        this.statisticsInterceptor = statisticsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statisticsInterceptor);
    }
}
