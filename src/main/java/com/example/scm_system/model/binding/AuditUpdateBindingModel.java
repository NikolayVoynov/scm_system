package com.example.scm_system.model.binding;

import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

import javax.validation.constraints.NotBlank;

public class AuditUpdateBindingModel {

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

    @NotBlank
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @NotBlank
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @NotBlank
    public DepartmentEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEnum department) {
        this.department = department;
    }

    @NotBlank
    public StatusAuditEnum getStatus() {
        return status;
    }

    public void setStatus(StatusAuditEnum status) {
        this.status = status;
    }

    @NotBlank
    public Integer getNumberNonconformities() {
        return numberNonconformities;
    }

    public void setNumberNonconformities(Integer numberNonconformities) {
        this.numberNonconformities = numberNonconformities;
    }

    @NotBlank
    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
