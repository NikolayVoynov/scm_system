package com.example.scm_system.repository;

import com.example.scm_system.model.entity.SafetyReportEntity;
import com.example.scm_system.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SafetyReportRepository extends JpaRepository<SafetyReportEntity, Long> {

    List<SafetyReportEntity> findBySendBy(UserEntity sendBy);

}
