package com.example.scm_system.service;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;
import com.example.scm_system.model.service.SafetyReportUpdateServiceModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface SafetyReportService {
    SafetyReportSendServiceModel sendSafetyReport(SafetyReportSendBindingModel safetyReportSendBindingModel, String ownerUsername) throws IOException;

    SafetyReportDetailsViewModel findById(Long id, String currentUser);

    boolean isOwner(String username, Long id);

    void deleteSafetyReport(Long id);

    void updateSafetyReport(SafetyReportUpdateServiceModel safetyReportUpdateServiceModel);

    List<SafetyReportDetailsViewModel> findSafetyReports(Principal user);
}
