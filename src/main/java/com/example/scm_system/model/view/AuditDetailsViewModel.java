package com.example.scm_system.model.view;

import com.example.scm_system.model.entity.NonconformityEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

import java.util.Set;

public class AuditDetailsViewModel {

    private Long id;
    private String refNumber;
    private String topic;
    private DepartmentEnum department;
    private String performedBy;
    private StatusAuditEnum status;
    private Integer numberNonconformities;
    //    private Set<NonconformityEntity> nonconformities;
    private String conclusion;
    //    private Set<EvidenceEntity> evidence;
    private boolean canDelete;

    public AuditDetailsViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public DepartmentEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEnum department) {
        this.department = department;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public StatusAuditEnum getStatus() {
        return status;
    }

    public void setStatus(StatusAuditEnum status) {
        this.status = status;
    }

    public Integer getNumberNonconformities() {
        return numberNonconformities;
    }

    public void setNumberNonconformities(Integer numberNonconformities) {
        this.numberNonconformities = numberNonconformities;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
