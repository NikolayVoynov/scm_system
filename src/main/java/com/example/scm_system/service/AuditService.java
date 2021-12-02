package com.example.scm_system.service;

import com.example.scm_system.model.binding.AuditAddBindingModel;
import com.example.scm_system.model.service.AuditAddServiceModel;
import com.example.scm_system.model.service.AuditUpdateServiceModel;
import com.example.scm_system.model.view.AuditDetailsViewModel;

public interface AuditService {
    AuditAddServiceModel addAudit(AuditAddBindingModel auditAddBindingModel, String ownerUsername);

    AuditDetailsViewModel findById(Long id, String currentUser);

    boolean isOwner(String username, Long id);

    void deleteAudit(Long id);

    void updateAudit(AuditUpdateServiceModel auditUpdateServiceModel);
}