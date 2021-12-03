package com.example.scm_system.model.view;

import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.LevelNonconformityEnum;
import com.example.scm_system.model.entity.enums.StatusNonconformityEnum;

import java.time.LocalDate;

public class NonconformityDetailsViewModel {

    private Long id;
    private String refNumber;
    private String description;
    private LevelNonconformityEnum level;
    private String raisedBy;
    private LocalDate raisedDate;
    private LocalDate closureDate;
    private String auditRefNumber;
    private StatusNonconformityEnum status;
    //    private Set<EvidenceEntity> evidence;
    private boolean canDelete;


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

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
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

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
