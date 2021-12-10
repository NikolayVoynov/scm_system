package com.example.scm_system.model.binding;

import com.example.scm_system.model.entity.enums.LevelNonconformityEnum;
import com.example.scm_system.model.entity.enums.StatusNonconformityEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class NonconformityUpdateBindingModel {

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

    @NotNull
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public LevelNonconformityEnum getLevel() {
        return level;
    }

    public void setLevel(LevelNonconformityEnum level) {
        this.level = level;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getRaisedDate() {
        return raisedDate;
    }

    public void setRaisedDate(LocalDate raisedDate) {
        this.raisedDate = raisedDate;
    }

    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDate closureDate) {
        this.closureDate = closureDate;
    }

    @NotNull
    public String getAuditRefNumber() {
        return auditRefNumber;
    }

    public void setAuditRefNumber(String auditRefNumber) {
        this.auditRefNumber = auditRefNumber;
    }

    @NotNull
    public StatusNonconformityEnum getStatus() {
        return status;
    }

    public void setStatus(StatusNonconformityEnum status) {
        this.status = status;
    }
}
