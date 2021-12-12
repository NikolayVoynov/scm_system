package com.example.scm_system.service;

import com.example.scm_system.model.binding.AuditAddBindingModel;
import com.example.scm_system.model.service.AuditAddServiceModel;
import com.example.scm_system.model.service.AuditUpdateServiceModel;
import com.example.scm_system.model.view.AuditDetailsViewModel;
import com.example.scm_system.model.view.AuditNonconformityViewModel;

import java.security.Principal;
import java.util.List;

public interface AuditService {
    AuditAddServiceModel addAudit(AuditAddBindingModel auditAddBindingModel, String ownerUsername);

    AuditDetailsViewModel findById(Long id, String currentUser);

    boolean isOwner(String username, Long id);

    void deleteAudit(Long id);

    void updateAudit(AuditUpdateServiceModel auditUpdateServiceModel);

    List<AuditNonconformityViewModel> getAllAudits();

    List<AuditDetailsViewModel> findAudits(Principal user);

    List<Long> getListNonconformitiesIdForAuditId(Long auditId);
}