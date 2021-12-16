package com.example.scm_system.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "evidence")
public class EvidenceEntity extends BaseEntity {

    private String url;
    private String publicId;

    public EvidenceEntity() {
    }

    @Lob
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Lob
    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
