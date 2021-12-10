package com.example.scm_system.repository;

import com.example.scm_system.model.entity.AuditEntity;
import com.example.scm_system.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, Long> {

    AuditEntity findByRefNumber(String refNumber);

    List<AuditEntity> findByPerformedBy(UserEntity performedBy);
}