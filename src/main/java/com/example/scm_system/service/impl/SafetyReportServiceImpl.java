package com.example.scm_system.service.impl;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.entity.*;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;
import com.example.scm_system.model.service.SafetyReportUpdateServiceModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;
import com.example.scm_system.repository.EvidenceRepository;
import com.example.scm_system.repository.SafetyReportRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.CloudinaryImage;
import com.example.scm_system.service.CloudinaryService;
import com.example.scm_system.service.SafetyReportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class SafetyReportServiceImpl implements SafetyReportService {

    private final SafetyReportRepository safetyReportRepository;
    private final EvidenceRepository evidenceRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public SafetyReportServiceImpl(SafetyReportRepository safetyReportRepository,
                                   EvidenceRepository evidenceRepository, CloudinaryService cloudinaryService, UserRepository userRepository, ModelMapper modelMapper) {
        this.safetyReportRepository = safetyReportRepository;
        this.evidenceRepository = evidenceRepository;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // SEND

    @Override
    public SafetyReportSendServiceModel sendSafetyReport(SafetyReportSendBindingModel safetyReportSendBindingModel,
                                                         String ownerUsername) throws IOException {

        UserEntity userEntity = userRepository.findByUsername(ownerUsername).orElseThrow();

        // Evidence - start

        EvidenceEntity firstEvidence = createEvidenceEntity(safetyReportSendBindingModel.getFirstEvidence());
        evidenceRepository.save(firstEvidence);
        EvidenceEntity savedEvidence = evidenceRepository.findByUrl(firstEvidence.getUrl()).orElseThrow();

        // Evidence - end

        SafetyReportSendServiceModel safetyReportSendServiceModel =
                modelMapper.map(safetyReportSendBindingModel, SafetyReportSendServiceModel.class);

        SafetyReportEntity newSafetyReport = modelMapper.map(safetyReportSendServiceModel, SafetyReportEntity.class);
        newSafetyReport.setSendBy(userEntity);
        newSafetyReport.setEvidence(Set.of(savedEvidence));

        SafetyReportEntity sendSafetyReport = safetyReportRepository.save(newSafetyReport);

        return modelMapper.map(sendSafetyReport, SafetyReportSendServiceModel.class);
    }

    private EvidenceEntity createEvidenceEntity(MultipartFile evidence) throws IOException {
        CloudinaryImage uploaded = cloudinaryService.upload(evidence);

        EvidenceEntity evidenceEntity = new EvidenceEntity();
        evidenceEntity.setPublicId(uploaded.getPublicId());
        evidenceEntity.setUrl(uploaded.getUrl());

        return evidenceEntity;
    }

    // DETAILS

    @Override
    public SafetyReportDetailsViewModel findById(Long id, String currentUser) {
        SafetyReportDetailsViewModel safetyReportDetailsViewModel =
                safetyReportRepository.
                        findById(id).
                        map(sr -> mapDetailsView(currentUser, sr)).
                        get();

        return safetyReportDetailsViewModel;
    }

    private SafetyReportDetailsViewModel mapDetailsView(String currentUser, SafetyReportEntity safetyReport) {
        SafetyReportDetailsViewModel safetyReportDetailsViewModel =
                modelMapper.map(safetyReport, SafetyReportDetailsViewModel.class);
        safetyReportDetailsViewModel.setCanDelete(isOwner(currentUser, safetyReport.getId()));
        safetyReportDetailsViewModel.setSendBy(safetyReport.getSendBy().getFirstName() + " " + safetyReport.getSendBy().getLastName());

        return safetyReportDetailsViewModel;
    }

    @Override
    public boolean isOwner(String username, Long id) {
        Optional<SafetyReportEntity> safetyReportOptional = safetyReportRepository.findById(id);
        Optional<UserEntity> user = userRepository.findByUsername(username);

        if (safetyReportOptional.isEmpty() || user.isEmpty()) {
            return false;
        } else {
            SafetyReportEntity safetyReportEntity = safetyReportOptional.get();

            return isAdmin(user.get()) ||
                    safetyReportEntity.getSendBy().getUsername().equals(username);
        }
    }


    private boolean isAdmin(UserEntity user) {
        return user.
                getRoles().
                stream().
                map(RoleEntity::getRole).
                anyMatch(r -> r == RoleEnum.ADMIN);
    }

    // UPDATE

    @Override
    public void updateSafetyReport(SafetyReportUpdateServiceModel safetyReportUpdateServiceModel) {

        SafetyReportEntity safetyReportEntity =
                safetyReportRepository.findById(safetyReportUpdateServiceModel.getId()).orElseThrow(() ->
                        new NoSuchElementException("Safety Report with id " + safetyReportUpdateServiceModel.getId() + " not found!"));

        safetyReportEntity.setTopic(safetyReportUpdateServiceModel.getTopic());
        safetyReportEntity.setOccurrenceDateTime(safetyReportUpdateServiceModel.getOccurrenceDateTime());
        safetyReportEntity.setDescription(safetyReportUpdateServiceModel.getDescription());
        safetyReportEntity.setStatus(safetyReportUpdateServiceModel.getStatus());


        safetyReportRepository.save(safetyReportEntity);
    }

    // DELETE

    @Override
    public void deleteSafetyReport(Long id) {
        safetyReportRepository.deleteById(id);
    }


}
