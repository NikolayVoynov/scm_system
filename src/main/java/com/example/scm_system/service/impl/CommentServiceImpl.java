package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.CommentEntity;
import com.example.scm_system.model.entity.NonconformityEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.service.CommentServiceModel;
import com.example.scm_system.model.view.CommentViewModel;
import com.example.scm_system.repository.CommentRepository;
import com.example.scm_system.repository.NonconformityRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.CommentService;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final NonconformityRepository nonconformityRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(NonconformityRepository nonconformityRepository,
                              UserRepository userRepository,
                              CommentRepository commentRepository) {
        this.nonconformityRepository = nonconformityRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public List<CommentViewModel> getComments(Long nonconformityId) {

        NonconformityEntity nonconformityOptional =
                nonconformityRepository.
                        findById(nonconformityId).
                        orElseThrow(() ->
                                new ObjectNotFoundException("Nonconformity with id " + nonconformityId + " not found!"));

        return nonconformityOptional.
                getComments().
                stream().
                map(this::mapsAsComment).
                collect(Collectors.toList());
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel commentServiceModel) {

        Objects.requireNonNull(commentServiceModel.getCreator());

        NonconformityEntity nonconformity = nonconformityRepository.
                findById(commentServiceModel.getNonconformityId()).
                orElseThrow(() ->
                        new ObjectNotFoundException("Nonconformity with id " + commentServiceModel.getNonconformityId() + " not found!"));

        UserEntity author = userRepository.
                findByUsername(commentServiceModel.getCreator()).
                orElseThrow(() ->
                        new ObjectNotFoundException("User with username " + commentServiceModel.getCreator() + " not found!"));

        CommentEntity newComment = new CommentEntity();
        newComment.setApproved(false);
        newComment.setContent(commentServiceModel.getMessage());
        newComment.setCreated(LocalDateTime.now());
        newComment.setNonconformity(nonconformity);
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
}
