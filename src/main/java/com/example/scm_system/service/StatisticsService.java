package com.example.scm_system.service;

import com.example.scm_system.model.view.StatisticsViewModel;

public interface StatisticsService {
    void onRequest();

    StatisticsViewModel getStatistics();
}
