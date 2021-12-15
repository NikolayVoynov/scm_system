package com.example.scm_system.model.binding;

import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class AuditAddBindingModel {

    private Long id;
    private String refNumber;
    private String topic;
    private DepartmentEnum department;
    private StatusAuditEnum status;
    private Integer numberNonconformities;
    private String conclusion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @NotNull
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @NotNull
    public DepartmentEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEnum department) {
        this.department = department;
    }

    @NotNull
    public StatusAuditEnum getStatus() {
        return status;
    }

    public void setStatus(StatusAuditEnum status) {
        this.status = status;
    }

    @NotNull
    @PositiveOrZero
    public Integer getNumberNonconformities() {
        return numberNonconformities;
    }

    public void setNumberNonconformities(Integer numberNonconformities) {
        this.numberNonconformities = numberNonconformities;
    }

    @NotEmpty
    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}