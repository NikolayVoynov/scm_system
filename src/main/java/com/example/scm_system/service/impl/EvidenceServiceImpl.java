package com.example.scm_system.service.impl;

import com.example.scm_system.model.entity.EvidenceEntity;
import com.example.scm_system.model.entity.SafetyReportEntity;
import com.example.scm_system.repository.EvidenceRepository;
import com.example.scm_system.repository.SafetyReportRepository;
import com.example.scm_system.service.EvidenceService;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EvidenceServiceImpl implements EvidenceService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }


    @Override
    public void deleteEvidenceWithId(List<Long> listEvidenceId) {

        for (Long id : listEvidenceId) {
            evidenceRepository.deleteById(id);
        }
    }
}
