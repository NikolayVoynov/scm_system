package com.example.scm_system.service;

import com.example.scm_system.model.service.CommentServiceModel;
import com.example.scm_system.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    List<CommentViewModel> getComments(Long auditId);

    CommentViewModel createComment(CommentServiceModel commentServiceModel);

    void deleteCommentsWithAuditId(Long auditId);

    void deleteListCommentWithId(List<Long> listCommentsId);

    List<Long> getListCommentsIdForAuditId(Long auditId);
}
