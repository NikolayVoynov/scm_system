package com.example.scm_system.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "safety_report")
public class SafetyReportEntity extends BaseEntity {

    private UserEntity sendBy;
    private String topic;
    private String description;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
