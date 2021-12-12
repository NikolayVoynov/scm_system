package com.example.scm_system.repository;

import com.example.scm_system.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    void deleteByAuditId(Long audit_id);

    List<CommentEntity> findByAuditId(Long auditId);
}
