package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.AuditEntity;
import com.example.scm_system.model.entity.CommentEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.service.CommentServiceModel;
import com.example.scm_system.model.view.CommentViewModel;
import com.example.scm_system.repository.AuditRepository;
import com.example.scm_system.repository.CommentRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.CommentService;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(AuditRepository auditRepository,
                              UserRepository userRepository,
                              CommentRepository commentRepository) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public List<CommentViewModel> getComments(Long auditId) {

        AuditEntity auditOptional =
                auditRepository.
                        findById(auditId).
                        orElseThrow(() ->
                                new ObjectNotFoundException("Audit with id " + auditId + " not found!"));

        return auditOptional.
                getComments().
                stream().
                map(this::mapsAsComment).
                collect(Collectors.toList());
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {

        Objects.requireNonNull(commentServiceModel.getCreator());

        AuditEntity audit = auditRepository.
                findById(commentServiceModel.getAuditId()).
                orElseThrow(() ->
                        new ObjectNotFoundException("Audit with id " + commentServiceModel.getAuditId() + " not found!"));

        UserEntity author = userRepository.
                findByUsername(commentServiceModel.getCreator()).
                orElseThrow(() ->
                        new ObjectNotFoundException("User with username " + commentServiceModel.getCreator() + " not found!"));

        CommentEntity newComment = new CommentEntity();
        newComment.setApproved(false);
        newComment.setContent(commentServiceModel.getMessage());
        newComment.setCreated(LocalDateTime.now());
        newComment.setAudit(audit);
        newComment.setAuthor(author);

        CommentEntity savedComment = commentRepository.save(newComment);

        return mapsAsComment(savedComment);
    }


    private CommentViewModel mapsAsComment(CommentEntity commentEntity) {
        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel.setCommentId(commentEntity.getId());
        commentViewModel.setCreated(commentEntity.getCreated());
        commentViewModel.setMessage(commentEntity.getContent());
        commentViewModel.setUser(commentEntity.getAuthor().getFirstName() + " " + commentEntity.getAuthor().getLastName());
        commentViewModel.setCanApprove(true);
        commentViewModel.setCanDelete(true);

        return commentViewModel;
    }

    @Override
    public void deleteCommentsWithAuditId(Long auditId) {

        commentRepository.deleteByAuditId(auditId);
    }

    @Override
    public void deleteListCommentWithId(List<Long> listCommentsId) {

        for (Long id : listCommentsId) {
            CommentEntity comment = commentRepository.
                    findById(id).
                    orElseThrow(() -> new ObjectNotFoundException("Comment with id " + id + " not found!"));

            comment.setAudit(null);

            commentRepository.save(comment);

            commentRepository.deleteById(id);
        }

    }

    @Override
    public List<Long> getListCommentsIdForAuditId(Long auditId) {

        List<CommentEntity> listComments = commentRepository.findByAuditId(auditId);

        List<Long> listCommentsId = new ArrayList<>();

        for (CommentEntity comment : listComments) {
            listCommentsId.add(comment.getId());
        }

        return listCommentsId;
    }
}
