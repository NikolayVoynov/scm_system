package com.example.scm_system.model.service;

import com.example.scm_system.model.entity.enums.LevelNonconformityEnum;
import com.example.scm_system.model.entity.enums.StatusNonconformityEnum;

import java.time.LocalDate;

public class NonconformityUpdateServiceModel {

    private Long id;
    private String refNumber;
    private String description;
    private LevelNonconformityEnum level;
    private LocalDate raisedDate;
    private LocalDate closureDate;
    private String auditRefNumber;
    private StatusNonconformityEnum status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LevelNonconformityEnum getLevel() {
        return level;
    }

    public void setLevel(LevelNonconformityEnum level) {
        this.level = level;
    }

    public LocalDate getRaisedDate() {
        return raisedDate;
    }

    public void setRaisedDate(LocalDate raisedDate) {
        this.raisedDate = raisedDate;
    }

    public LocalDate getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDate closureDate) {
        this.closureDate = closureDate;
    }

    public String getAuditRefNumber() {
        return auditRefNumber;
    }

    public void setAuditRefNumber(String auditRefNumber) {
        this.auditRefNumber = auditRefNumber;
    }

    public StatusNonconformityEnum getStatus() {
        return status;
    }

    public void setStatus(StatusNonconformityEnum status) {
        this.status = status;
    }
}
