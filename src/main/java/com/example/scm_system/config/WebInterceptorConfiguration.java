package com.example.scm_system.config;

import com.example.scm_system.web.interceptors.AmendRequestInterceptor;
import com.example.scm_system.web.interceptors.StatisticsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfiguration implements WebMvcConfigurer {

    private final StatisticsInterceptor statisticsInterceptor;
    private final AmendRequestInterceptor amendRequestInterceptor;

    public WebInterceptorConfiguration(StatisticsInterceptor statisticsInterceptor, AmendRequestInterceptor amendRequestInterceptor) {
        this.statisticsInterceptor = statisticsInterceptor;
        this.amendRequestInterceptor = amendRequestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statisticsInterceptor);
        registry.addInterceptor(amendRequestInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }
}
