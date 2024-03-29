package com.example.scm_system.model.view;

import com.example.scm_system.model.entity.EvidenceEntity;
import com.example.scm_system.model.entity.enums.StatusSafetyReportEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SafetyReportDetailsViewModel {

    private Long id;
    private String topic;
    private String sendBy;
    private LocalDateTime occurrenceDateTime;
    private String description;
    private StatusSafetyReportEnum status;
    private Set<EvidenceEntity> evidence;
    private boolean canDelete;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public LocalDateTime getOccurrenceDateTime() {
        return occurrenceDateTime;
    }

    public void setOccurrenceDateTime(LocalDateTime occurrenceDateTime) {
        this.occurrenceDateTime = occurrenceDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<EvidenceEntity> getEvidence() {
        return evidence;
    }

    public void setEvidence(Set<EvidenceEntity> evidence) {
        this.evidence = evidence;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public StatusSafetyReportEnum getStatus() {
        return status;
    }

    public void setStatus(StatusSafetyReportEnum status) {
        this.status = status;
    }
}
