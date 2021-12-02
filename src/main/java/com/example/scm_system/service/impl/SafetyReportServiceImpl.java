package com.example.scm_system.service.impl;

import com.example.scm_system.model.binding.SafetyReportSendBindingModel;
import com.example.scm_system.model.entity.AuditEntity;
import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.SafetyReportEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.SafetyReportSendServiceModel;
import com.example.scm_system.model.service.SafetyReportUpdateServiceModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;
import com.example.scm_system.repository.SafetyReportRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.SafetyReportService;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SafetyReportServiceImpl implements SafetyReportService {

    private final SafetyReportRepository safetyReportRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public SafetyReportServiceImpl(SafetyReportRepository safetyReportRepository,
                                   UserRepository userRepository, ModelMapper modelMapper) {
        this.safetyReportRepository = safetyReportRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // SEND

    @Override
    public SafetyReportSendServiceModel sendSafetyReport(SafetyReportSendBindingModel safetyReportSendBindingModel,
                                                         String ownerUsername) {

        UserEntity userEntity = userRepository.findByUsername(ownerUsername).orElseThrow();
        SafetyReportSendServiceModel safetyReportSendServiceModel =
                modelMapper.map(safetyReportSendBindingModel, SafetyReportSendServiceModel.class);

        SafetyReportEntity newSafetyReport = modelMapper.map(safetyReportSendServiceModel, SafetyReportEntity.class);
        newSafetyReport.setSendBy(userEntity);

        SafetyReportEntity sendSafetyReport = safetyReportRepository.save(newSafetyReport);

        return modelMapper.map(sendSafetyReport, SafetyReportSendServiceModel.class);
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
