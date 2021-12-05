package com.example.scm_system.model.service;

public class CommentServiceModel {

    private Long nonconformityId;
    private String message;
    private String creator;

    public Long getNonconformityId() {
        return nonconformityId;
    }

    public void setNonconformityId(Long nonconformityId) {
        this.nonconformityId = nonconformityId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
