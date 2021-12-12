package com.example.scm_system.service.impl;

import com.example.scm_system.model.binding.AuditAddBindingModel;
import com.example.scm_system.model.entity.*;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.AuditAddServiceModel;
import com.example.scm_system.model.service.AuditUpdateServiceModel;
import com.example.scm_system.model.view.AuditDetailsViewModel;
import com.example.scm_system.model.view.AuditNonconformityViewModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;
import com.example.scm_system.repository.AuditRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.AuditService;
import com.example.scm_system.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuditServiceImpl(AuditRepository auditRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // ADD

    @Override
    public AuditAddServiceModel addAudit(AuditAddBindingModel auditAddBindingModel, String ownerUsername) {

        UserEntity userEntity = userRepository.findByUsername(ownerUsername).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + ownerUsername + " not found!"));
        AuditAddServiceModel auditAddServiceModel = modelMapper.map(auditAddBindingModel, AuditAddServiceModel.class);
        AuditEntity newAudit = modelMapper.map(auditAddServiceModel, AuditEntity.class);
        newAudit.setPerformedBy(userEntity);

        AuditEntity savedAudit = auditRepository.save(newAudit);

        return modelMapper.map(savedAudit, AuditAddServiceModel.class);
    }

    // DETAILS

    @Override
    public AuditDetailsViewModel findById(Long id, String currentUser) {
        AuditDetailsViewModel auditDetailsView =
                auditRepository.
                        findById(id).
                        map(a -> mapDetailsView(currentUser, a)).
                        get();

        return auditDetailsView;
    }

    @Override
    public boolean isOwner(String username, Long id) {
        Optional<AuditEntity> auditOptional = auditRepository.
                findById(id);
        Optional<UserEntity> user = userRepository.
                findByUsername(username);

        if (auditOptional.isEmpty() || user.isEmpty()) {
            return false;
        } else {
            AuditEntity auditEntity = auditOptional.get();

            return isAdmin(user.get()) ||
                    auditEntity.getPerformedBy().getUsername().equals(username);
        }
    }

    private boolean isAdmin(UserEntity user) {
        return user.
                getRoles().
                stream().
                map(RoleEntity::getRole).
                anyMatch(r -> r == RoleEnum.ADMIN);
    }

    private AuditDetailsViewModel mapDetailsView(String currentUser, AuditEntity audit) {
        AuditDetailsViewModel auditDetailsView = modelMapper.map(audit, AuditDetailsViewModel.class);
        auditDetailsView.setCanDelete(isOwner(currentUser, audit.getId()));
        auditDetailsView.setPerformedBy(audit.getPerformedBy().getFirstName() + " " + audit.getPerformedBy().getLastName());

        return auditDetailsView;
    }

    // UPDATE

    @Override
    public void updateAudit(AuditUpdateServiceModel auditUpdateServiceModel) {

        AuditEntity auditEntity =
                auditRepository.findById(auditUpdateServiceModel.getId()).orElseThrow(
                        () ->
                                new ObjectNotFoundException("Audit with id " + auditUpdateServiceModel.getId() + " not found!"));

        auditEntity.setRefNumber(auditUpdateServiceModel.getRefNumber());
        auditEntity.setTopic(auditUpdateServiceModel.getTopic());
        auditEntity.setDepartment(auditUpdateServiceModel.getDepartment());
        auditEntity.setStatus(auditUpdateServiceModel.getStatus());
        auditEntity.setNumberNonconformities(auditUpdateServiceModel.getNumberNonconformities());
        auditEntity.setConclusion(auditUpdateServiceModel.getConclusion());

        auditRepository.save(auditEntity);
    }


    // DELETE

    @Override
    public void deleteAudit(Long id) {

        auditRepository.deleteById(id);
    }


    // ADD NONCONFORMITY

    @Override
    public List<AuditNonconformityViewModel> getAllAudits() {
        return auditRepository.findAll()
                .stream()
                .map(auditEntity -> {
                    AuditNonconformityViewModel auditNonconformityViewModel = new AuditNonconformityViewModel();
                    auditNonconformityViewModel.setRefNumber(auditEntity.getRefNumber());

                    return auditNonconformityViewModel;
                }).collect(Collectors.toList());
    }

    // DASHBOARD

    @Override
    public List<AuditDetailsViewModel> findAudits(Principal user) {
        UserEntity userEntity = userRepository.findByUsername(user.getName()).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + user.getName() + " not found!"));

        boolean isAdmin = isAdmin(userEntity);

        List<AuditDetailsViewModel> audits = new ArrayList<>();

        if (isAdmin) {
            audits = auditRepository.
                    findAll().
                    stream().
                    map(auditEntity -> {
                        AuditDetailsViewModel auditDetailsViewModel = modelMapper.map(auditEntity, AuditDetailsViewModel.class);
                        auditDetailsViewModel.setPerformedBy(auditEntity.getPerformedBy().getUsername());

                        return auditDetailsViewModel;
                    }).
                    collect(Collectors.toList());
        } else {
            audits = auditRepository.
                    findByPerformedBy(userEntity).
                    stream().
                    map(auditEntity -> {
                        AuditDetailsViewModel auditDetailsViewModel = modelMapper.map(auditEntity, AuditDetailsViewModel.class);
                        auditDetailsViewModel.setPerformedBy(auditEntity.getPerformedBy().getUsername());

                        return auditDetailsViewModel;
                    }).
                    collect(Collectors.toList());
        }

        return audits;
    }


}