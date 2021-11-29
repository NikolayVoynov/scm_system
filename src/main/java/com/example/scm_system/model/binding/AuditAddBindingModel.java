package com.example.scm_system.model.binding;

import com.example.scm_system.model.entity.EvidenceEntity;
import com.example.scm_system.model.entity.NonconformityEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

import java.util.Set;

public class AuditAddBindingModel {

    private Long id;
    private String refNumber;
    private String topic;
    private DepartmentEnum department;
    private UserEntity performedBy;
    private StatusAuditEnum status;
    private Set<NonconformityEntity> nonconformities;
    private String conclusion;
    private Set<EvidenceEntity> evidence;

}
