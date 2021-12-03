package com.example.scm_system.repository;

import com.example.scm_system.model.entity.NonconformityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NonconformityRepository extends JpaRepository<NonconformityEntity, Long> {
}
