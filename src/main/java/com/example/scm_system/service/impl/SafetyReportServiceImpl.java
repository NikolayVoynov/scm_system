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
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

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

        UserEntity userEntity = userRepository.findByUsername(ownerUsername).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + ownerUsername + " not found!"));

        // Evidence - start

        EvidenceEntity firstSavedEvidence = null;
        EvidenceEntity secondSavedEvidence = null;

        if (safetyReportSendBindingModel.getFirstEvidence() != null) {
            EvidenceEntity firstEvidence = createEvidenceEntity(safetyReportSendBindingModel.getFirstEvidence());
            evidenceRepository.save(firstEvidence);

            firstSavedEvidence = evidenceRepository.findByUrl(firstEvidence.getUrl()).orElseThrow(() ->
                    new ObjectNotFoundException("First evidence not found!"));
        }

        if (safetyReportSendBindingModel.getSecondEvidence() != null) {
            EvidenceEntity secondEvidence = createEvidenceEntity(safetyReportSendBindingModel.getSecondEvidence());
            evidenceRepository.save(secondEvidence);

            secondSavedEvidence = evidenceRepository.findByUrl(secondEvidence.getUrl()).orElseThrow(() ->
                    new ObjectNotFoundException("Second evidence not found!"));
        }

        List<EvidenceEntity> listEvidence = new ArrayList<>();
        listEvidence.add(firstSavedEvidence);
        listEvidence.add(secondSavedEvidence);

        Set<EvidenceEntity> setOfEvidence = new HashSet<>();

        for (EvidenceEntity evidence : listEvidence) {
            if (evidence != null) {
                setOfEvidence.add(evidence);
            }
        }

        // Evidence - end

        SafetyReportSendServiceModel safetyReportSendServiceModel =
                modelMapper.map(safetyReportSendBindingModel, SafetyReportSendServiceModel.class);

        SafetyReportEntity newSafetyReport = modelMapper.map(safetyReportSendServiceModel, SafetyReportEntity.class);
        newSafetyReport.setSendBy(userEntity);
        newSafetyReport.setEvidence(setOfEvidence);

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
                        new ObjectNotFoundException("Safety Report with id " + safetyReportUpdateServiceModel.getId() + " not found!"));

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

    @Override
    public List<Long> getListEvidenceIdForSafetyReport(Long id) {

        SafetyReportEntity safetyReportEntity = safetyReportRepository.
                findById(id).
                orElseThrow(() ->
                        new ObjectNotFoundException("User with id " + id + " not found!"));

        Set<EvidenceEntity> setEvidence = safetyReportEntity.getEvidence();

        List<Long> listIdEvidence = new ArrayList<>();

        for (EvidenceEntity evidenceEntity : setEvidence) {
            listIdEvidence.add(evidenceEntity.getId());
        }

        return listIdEvidence;
    }


    // DASHBOARD

    @Override
    public List<SafetyReportDetailsViewModel> findSafetyReports(Principal user) {
        UserEntity userEntity = userRepository.findByUsername(user.getName()).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + user.getName() + " not found!"));

        boolean isAdmin = isAdmin(userEntity);

        List<SafetyReportDetailsViewModel> safetyReports = new ArrayList<>();

        if (isAdmin) {
            safetyReports = safetyReportRepository.
                    findAll().
                    stream().
                    map(safetyReportEntity -> {
                        SafetyReportDetailsViewModel reportDetailsViewModel = modelMapper.map(safetyReportEntity, SafetyReportDetailsViewModel.class);
                        reportDetailsViewModel.setSendBy(safetyReportEntity.getSendBy().getUsername());

                        return reportDetailsViewModel;
                    }).
                    collect(Collectors.toList());
        } else {
            safetyReports = safetyReportRepository.
                    findBySendBy(userEntity).
                    stream().
                    map(safetyReportEntity -> {
                        SafetyReportDetailsViewModel reportDetailsViewModel = modelMapper.map(safetyReportEntity, SafetyReportDetailsViewModel.class);
                        reportDetailsViewModel.setSendBy(safetyReportEntity.getSendBy().getUsername());

                        return reportDetailsViewModel;
                    }).
                    collect(Collectors.toList());
        }

        return safetyReports;
    }


}