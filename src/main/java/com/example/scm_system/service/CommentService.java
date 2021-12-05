package com.example.scm_system.service;

import com.example.scm_system.model.service.CommentServiceModel;
import com.example.scm_system.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    List<CommentViewModel> getComments(Long nonconformityId);

    CommentViewModel createComment(CommentServiceModel commentServiceModel);
}
