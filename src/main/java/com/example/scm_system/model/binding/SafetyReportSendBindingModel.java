package com.example.scm_system.model.binding;

import com.example.scm_system.model.entity.EvidenceEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.StatusAuditEnum;
import com.example.scm_system.model.entity.enums.StatusSafetyReportEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class SafetyReportSendBindingModel {

    private Long id;
    private String topic;
    private LocalDateTime occurrenceDateTime;
    private String description;
    private StatusSafetyReportEnum status;
    private MultipartFile firstEvidence;
//    private Set<EvidenceEntity> evidence;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    public LocalDateTime getOccurrenceDateTime() {
        return occurrenceDateTime;
    }

    public void setOccurrenceDateTime(LocalDateTime occurrenceDateTime) {
        this.occurrenceDateTime = occurrenceDateTime;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public StatusSafetyReportEnum getStatus() {
        return status;
    }

    public void setStatus(StatusSafetyReportEnum status) {
        this.status = status;
    }

    public MultipartFile getFirstEvidence() {
        return firstEvidence;
    }

    public void setFirstEvidence(MultipartFile firstEvidence) {
        this.firstEvidence = firstEvidence;
    }
}
