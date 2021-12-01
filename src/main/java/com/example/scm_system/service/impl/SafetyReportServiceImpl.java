package com.example.scm_system.service.impl;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;
import com.example.scm_system.service.SafetyReportService;
import org.springframework.stereotype.Service;

@Service
public class SafetyReportServiceImpl implements SafetyReportService {

    @Override
    public SafetyReportSendServiceModel sendSafetyReport(SafetyReportSendBindingModel safetyReportSendBindingModel, String ownerUsername) {
        return null;
    }
}
