package com.example.scm_system.model.service;

import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

public class AuditUpdateServiceModel {

    private Long id;
    private String refNumber;
    private String topic;
    private DepartmentEnum department;
    private StatusAuditEnum status;
    private Integer numberNonconformities;
    private String conclusion;
//    private Set<EvidenceEntity> evidence;


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
}