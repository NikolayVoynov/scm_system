package com.example.scm_system.repository;

import com.example.scm_system.model.entity.SafetyReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafetyReportRepository extends JpaRepository<SafetyReportEntity, Long> {
}
