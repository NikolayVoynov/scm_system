package com.example.scm_system.service.impl;

import com.example.scm_system.model.binding.AuditAddBindingModel;
import com.example.scm_system.model.entity.AuditEntity;
import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.AuditAddServiceModel;
import com.example.scm_system.model.service.AuditUpdateServiceModel;
import com.example.scm_system.model.view.AuditDetailsView;
import com.example.scm_system.repository.AuditRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.AuditService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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

        UserEntity userEntity = userRepository.findByUsername(ownerUsername).orElseThrow();
        AuditAddServiceModel auditAddServiceModel = modelMapper.map(auditAddBindingModel, AuditAddServiceModel.class);
        AuditEntity newAudit = modelMapper.map(auditAddServiceModel, AuditEntity.class);
        newAudit.setPerformedBy(userEntity);

        AuditEntity savedAudit = auditRepository.save(newAudit);

        return modelMapper.map(savedAudit, AuditAddServiceModel.class);
    }

    @Override
    public AuditDetailsView findById(Long id, String currentUser) {
        AuditDetailsView auditDetailsView =
                auditRepository.
                        findById(id).
                        map(a -> mapDetailsView(currentUser, a)).
                        get();

        return auditDetailsView;
    }

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

    private AuditDetailsView mapDetailsView(String currentUser, AuditEntity audit) {
        AuditDetailsView auditDetailsView = modelMapper.map(audit, AuditDetailsView.class);
        auditDetailsView.setCanDelete(isOwner(currentUser, audit.getId()));
        auditDetailsView.setPerformedBy(audit.getPerformedBy().getFirstName() + " " + audit.getPerformedBy().getLastName());

        return auditDetailsView;
    }

    // UPDATE

    @Override
    public void updateAudit(AuditUpdateServiceModel auditUpdateServiceModel) {

        AuditEntity auditEntity =
                auditRepository.findById(auditUpdateServiceModel.getId()).orElseThrow(() ->
                        new NoSuchElementException("Audit with id " + auditUpdateServiceModel.getId() + " not found!"));

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


}
