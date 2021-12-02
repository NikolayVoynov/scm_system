package com.example.scm_system.model.entity;

import com.example.scm_system.model.entity.enums.StatusSafetyReportEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "safety_report")
public class SafetyReportEntity extends BaseEntity {

    private UserEntity sendBy;
    private String topic;
    private LocalDateTime occurrenceDateTime;
    private String description;
    private StatusSafetyReportEnum status;
//    private Set<EvidenceEntity> evidence;

    public SafetyReportEntity() {
    }

    @ManyToOne
    public UserEntity getSendBy() {
        return sendBy;
    }

    public void setSendBy(UserEntity sendBy) {
        this.sendBy = sendBy;
    }

    @Column(nullable = false)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Column(nullable = false)
    public LocalDateTime getOccurrenceDateTime() {
        return occurrenceDateTime;
    }

    public void setOccurrenceDateTime(LocalDateTime occurrenceDateTime) {
        this.occurrenceDateTime = occurrenceDateTime;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    public StatusSafetyReportEnum getStatus() {
        return status;
    }

    public void setStatus(StatusSafetyReportEnum status) {
        this.status = status;
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
