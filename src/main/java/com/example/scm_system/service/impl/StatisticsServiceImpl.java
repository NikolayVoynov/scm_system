package com.example.scm_system.service.impl;

import com.example.scm_system.model.view.StatisticsViewModel;
import com.example.scm_system.service.StatisticsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private int authorizedRequests, anonymousRequests;

    @Override
    public void onRequest() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && (authentication.getPrincipal() instanceof UserDetails)) {
            authorizedRequests++;
        } else {
            anonymousRequests++;
        }
    }

    @Override
    public StatisticsViewModel getStatistics() {
        return new StatisticsViewModel(authorizedRequests, anonymousRequests);
    }
}
