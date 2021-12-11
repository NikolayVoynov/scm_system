package com.example.scm_system.model.entity;

import com.example.scm_system.model.entity.enums.DepartmentEnum;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;

import javax.persistence.*;
import java.util.List;
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
    private List<NonconformityEntity> nonconformities;
    private List<CommentEntity> comments;
    private String conclusion;


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

    @OneToMany(mappedBy = "audit")
    public List<NonconformityEntity> getNonconformities() {
        return nonconformities;
    }

    public void setNonconformities(List<NonconformityEntity> nonconformities) {
        this.nonconformities = nonconformities;
    }

    @OneToMany(mappedBy = "audit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
