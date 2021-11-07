package com.example.scm_system.model.entity;

import com.example.scm_system.model.entity.enums.LevelNonconformityEnum;
import com.example.scm_system.model.entity.enums.StatusNonconformityEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nonconformities")
public class NonconformityEntity extends BaseEntity{

    private String refNumber;
    private String description;
    private LevelNonconformityEnum level;
    private UserEntity raisedBy;
    private LocalDate raisedDate;
    private LocalDate closureDate;
    private AuditEntity audit;
    private StatusNonconformityEnum status;

    public NonconformityEntity() {
    }

    @Column(nullable = false)
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    public LevelNonconformityEnum getLevel() {
        return level;
    }

    public void setLevel(LevelNonconformityEnum level) {
        this.level = level;
    }

    @ManyToOne
    public UserEntity getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(UserEntity raisedBy) {
        this.raisedBy = raisedBy;
    }

    @Column(nullable = false)
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

    @ManyToOne
    public AuditEntity getAudit() {
        return audit;
    }

    public void setAudit(AuditEntity audit) {
        this.audit = audit;
    }

    @Enumerated(EnumType.STRING)
    public StatusNonconformityEnum getStatus() {
        return status;
    }

    public void setStatus(StatusNonconformityEnum status) {
        this.status = status;
    }
}
