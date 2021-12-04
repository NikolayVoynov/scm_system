package com.example.scm_system.repository;

import com.example.scm_system.model.entity.EvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvidenceRepository extends JpaRepository<EvidenceEntity, Long> {

    Optional<EvidenceEntity> findByUrl(String url);
}
