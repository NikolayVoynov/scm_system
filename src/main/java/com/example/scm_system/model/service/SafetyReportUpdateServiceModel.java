package com.example.scm_system.model.service;

import com.example.scm_system.model.entity.enums.StatusSafetyReportEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SafetyReportUpdateServiceModel {

    private Long id;
    private String topic;
    private LocalDateTime occurrenceDateTime;
    private String description;
    private StatusSafetyReportEnum status;


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
}
