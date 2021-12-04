package com.example.scm_system.model.service;

import com.example.scm_system.model.entity.EvidenceEntity;
import com.example.scm_system.model.entity.enums.StatusSafetyReportEnum;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class SafetyReportSendServiceModel {

    private Long id;
    private String topic;
    private LocalDateTime occurrenceDateTime;
    private StatusSafetyReportEnum status;
    private String description;
    private Set<EvidenceEntity> evidence;


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

    public StatusSafetyReportEnum getStatus() {
        return status;
    }

    public void setStatus(StatusSafetyReportEnum status) {
        this.status = status;
    }

    public Set<EvidenceEntity> getEvidence() {
        return evidence;
    }

    public void setEvidence(Set<EvidenceEntity> evidence) {
        this.evidence = evidence;
    }
}
