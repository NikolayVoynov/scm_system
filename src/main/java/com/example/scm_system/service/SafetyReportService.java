package com.example.scm_system.service;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;

public interface SafetyReportService {
    SafetyReportSendServiceModel sendSafetyReport(SafetyReportSendBindingModel safetyReportSendBindingModel, String ownerUsername);
}
