package com.example.scm_system.web;

import com.example.scm_system.model.binding.CommentBindingModel;
import com.example.scm_system.model.service.CommentServiceModel;
import com.example.scm_system.model.validation.ApiError;
import com.example.scm_system.model.view.CommentViewModel;
import com.example.scm_system.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class CommentsRestController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentsRestController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/restapi/{auditId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long auditId,
                                                              Principal principal) {

        return ResponseEntity.ok(commentService.getComments(auditId));
    }

    @PostMapping(value = "/restapi/{auditId}/comments",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<CommentViewModel> postComment(@AuthenticationPrincipal UserDetails principal,
                                                        @PathVariable Long auditId,
                                                        @RequestParam Map<String, String> requestParams) {

        CommentServiceModel commentServiceModel =
                modelMapper.map("", CommentServiceModel.class);
        commentServiceModel.setCreator(principal.getUsername());
        commentServiceModel.setAuditId(auditId);
        commentServiceModel.setMessage(requestParams.get("message"));

        CommentViewModel commentViewModel = commentService.createComment(commentServiceModel);

        URI locationOfComment =
                URI.create(String.format("/restapi/%s/comments/%s", auditId, commentViewModel.getCommentId()));

        return ResponseEntity.
                created(locationOfComment).
                body(commentViewModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        exc.getFieldErrors().forEach(fe ->
                apiError.addFieldWithError(fe.getField()));

        return ResponseEntity.badRequest().body(apiError);
    }


}
