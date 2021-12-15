package com.example.scm_system.web.interceptors;

import com.example.scm_system.service.StatisticsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatisticsInterceptor implements HandlerInterceptor {

    private final StatisticsService statisticsService;

    public StatisticsInterceptor(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        statisticsService.onRequest();

        return true;
    }
}
