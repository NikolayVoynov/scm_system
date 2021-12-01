package com.example.scm_system.model.entity;

import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "audits")
public class AuditEntity extends BaseEntity{

    private String refNumber;
    private String topic;
    private DepartmentEnum department;
    private UserEntity performedBy;
    private StatusAuditEnum status;
    private Integer numberNonconformities;
    private Set<NonconformityEntity> nonconformities;
    private String conclusion;
//    private Set<EvidenceEntity> evidence;


    public AuditEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    @Column(nullable = false)
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

    @ManyToOne
    public UserEntity getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(UserEntity performedBy) {
        this.performedBy = performedBy;
    }

    @Enumerated(EnumType.STRING)
    public StatusAuditEnum getStatus() {
        return status;
    }

    public void setStatus(StatusAuditEnum status) {
        this.status = status;
    }

    @Column(nullable = false)
    public Integer getNumberNonconformities() {
        return numberNonconformities;
    }

    public void setNumberNonconformities(Integer numberNonconformities) {
        this.numberNonconformities = numberNonconformities;
    }

    @OneToMany
    public Set<NonconformityEntity> getNonconformities() {
        return nonconformities;
    }

    public void setNonconformities(Set<NonconformityEntity> nonconformities) {
        this.nonconformities = nonconformities;
    }

    @Column(nullable = false)
    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

//    @OneToMany
//    public Set<EvidenceEntity> getEvidence() {
//        return evidence;
//    }
//
//    public void setEvidence(Set<EvidenceEntity> evidence) {
//        this.evidence = evidence;
//    }
}
