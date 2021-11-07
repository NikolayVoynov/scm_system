package com.example.scm_system.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "evidence")
public class EvidenceEntity extends BaseEntity{

    private String url;
    private AuditEntity audit;
    private SafetyReportEntity report;

    public EvidenceEntity() {
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne
    public AuditEntity getAudit() {
        return audit;
    }

    public void setAudit(AuditEntity audit) {
        this.audit = audit;
    }

    @ManyToOne
    public SafetyReportEntity getReport() {
        return report;
    }

    public void setReport(SafetyReportEntity report) {
        this.report = report;
    }
}
