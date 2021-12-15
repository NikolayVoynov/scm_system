package com.example.scm_system.web.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AmendRequestInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AmendRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String method = request.getMethod();

        if (method == null) {
            return false;
        }

        if (method.equals("PATCH")) {
            logger.info("Update request intercepted on: " + request.getRequestURI());
        } else if (method.equals("DELETE")) {
            logger.info("Delete request intercepted on: " + request.getRequestURI());
        }

        return true;
    }
}
