package com.example.scm_system.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentBindingModel {

    private String message;

    @NotBlank
    @Size(min = 1)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
