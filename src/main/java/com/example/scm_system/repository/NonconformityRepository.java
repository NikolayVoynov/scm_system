package com.example.scm_system.repository;

import com.example.scm_system.model.entity.NonconformityEntity;
import com.example.scm_system.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface NonconformityRepository extends JpaRepository<NonconformityEntity, Long> {

    List<NonconformityEntity> findByRaisedBy(UserEntity raisedBy);

    void deleteByAuditId(Long audit_id);

    List<NonconformityEntity> findByAuditId(Long auditId);
}
