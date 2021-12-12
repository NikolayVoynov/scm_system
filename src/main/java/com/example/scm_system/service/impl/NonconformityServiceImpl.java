package com.example.scm_system.service.impl;

import com.example.scm_system.model.binding.NonconformityAddBindingModel;
import com.example.scm_system.model.entity.AuditEntity;
import com.example.scm_system.model.entity.NonconformityEntity;
import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import com.example.scm_system.model.service.NonconformityAddServiceModel;
import com.example.scm_system.model.service.NonconformityUpdateServiceModel;
import com.example.scm_system.model.view.NonconformityDetailsViewModel;
import com.example.scm_system.model.view.SafetyReportDetailsViewModel;
import com.example.scm_system.repository.AuditRepository;
import com.example.scm_system.repository.NonconformityRepository;
import com.example.scm_system.repository.UserRepository;
import com.example.scm_system.service.NonconformityService;
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
public class NonconformityServiceImpl implements NonconformityService {

    private final NonconformityRepository nonconformityRepository;
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;
    private final ModelMapper modelMapper;

    public NonconformityServiceImpl(NonconformityRepository nonconformityRepository,
                                    UserRepository userRepository, AuditRepository auditRepository, ModelMapper modelMapper) {
        this.nonconformityRepository = nonconformityRepository;
        this.userRepository = userRepository;
        this.auditRepository = auditRepository;
        this.modelMapper = modelMapper;
    }

    //ADD

    @Override
    public NonconformityAddServiceModel addNonconformity(NonconformityAddBindingModel nonconformityAddBindingModel,
                                                         String ownerUsername) {

        UserEntity userEntity = userRepository.findByUsername(ownerUsername).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + ownerUsername + " not found!"));
        NonconformityAddServiceModel nonconformityAddServiceModel =
                modelMapper.map(nonconformityAddBindingModel, NonconformityAddServiceModel.class);
        NonconformityEntity newNonconformity = modelMapper.map(nonconformityAddServiceModel, NonconformityEntity.class);
        newNonconformity.setRaisedBy(userEntity);

        AuditEntity auditEntity = auditRepository.findByRefNumber(nonconformityAddBindingModel.getAuditRefNumber());
        newNonconformity.setAudit(auditEntity);

        NonconformityEntity savedNonconformity = nonconformityRepository.save(newNonconformity);

        return modelMapper.map(savedNonconformity, NonconformityAddServiceModel.class);
    }

    // DETAILS

    @Override
    public NonconformityDetailsViewModel findById(Long id, String currentUser) {
        NonconformityDetailsViewModel nonconformityDetailsViewModel =
                nonconformityRepository.
                        findById(id).
                        map(n -> mapDetailsView(currentUser, n)).
                        get();

        return nonconformityDetailsViewModel;
    }

    private NonconformityDetailsViewModel mapDetailsView(String currentUser, NonconformityEntity nonconformity) {
        NonconformityDetailsViewModel nonconformityDetailsViewModel =
                modelMapper.map(nonconformity, NonconformityDetailsViewModel.class);
        nonconformityDetailsViewModel.setCanDelete(isOwner(currentUser, nonconformity.getId()));
        nonconformityDetailsViewModel.
                setRaisedBy(nonconformity.getRaisedBy().getFirstName() + " " + nonconformity.getRaisedBy().getLastName());

        return nonconformityDetailsViewModel;
    }

    @Override
    public boolean isOwner(String username, Long id) {
        Optional<NonconformityEntity> nonconformityOptional = nonconformityRepository.
                findById(id);
        Optional<UserEntity> user = userRepository.
                findByUsername(username);

        if (nonconformityOptional.isEmpty() || user.isEmpty()) {
            return false;
        } else {
            NonconformityEntity nonconformityEntity = nonconformityOptional.get();

            return isAdmin(user.get()) ||
                    nonconformityEntity.getRaisedBy().getUsername().equals(username);
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
    public void updateNonconformity(NonconformityUpdateServiceModel nonconformityUpdateServiceModel) {

        NonconformityEntity nonconformity =
                nonconformityRepository.findById(nonconformityUpdateServiceModel.getId()).orElseThrow(() ->
                        new ObjectNotFoundException("Nonconformity with id " + nonconformityUpdateServiceModel.getId() + " not found!"));

        AuditEntity auditEntity = auditRepository.findByRefNumber(nonconformityUpdateServiceModel.getAuditRefNumber());
        nonconformity.setAudit(auditEntity);
        nonconformity.setRefNumber(nonconformityUpdateServiceModel.getRefNumber());
        nonconformity.setDescription(nonconformityUpdateServiceModel.getDescription());
        nonconformity.setLevel(nonconformityUpdateServiceModel.getLevel());
        nonconformity.setStatus(nonconformityUpdateServiceModel.getStatus());
        nonconformity.setRaisedDate(nonconformityUpdateServiceModel.getRaisedDate());
        nonconformity.setClosureDate(nonconformityUpdateServiceModel.getClosureDate());

        nonconformityRepository.save(nonconformity);
    }

    // DELETE NONCONFORMITY

    @Override
    public void deleteNonconformity(Long id) {

        nonconformityRepository.deleteById(id);
    }

    // DELETE AUDIT

    @Override
    public void deleteNonconformityWithAuditId(Long auditId) {

        nonconformityRepository.deleteByAuditId(auditId);
    }

    @Override
    public void deleteListNonconformityWithId(List<Long> listNonconformitiesId) {

        for (Long id : listNonconformitiesId) {
            NonconformityEntity nonconformity = nonconformityRepository.
                    findById(id).
                    orElseThrow(() ->
                    new ObjectNotFoundException("Nonconformity with id " + id + " not found!"));

            nonconformity.setAudit(null);

            nonconformityRepository.save(nonconformity);

            nonconformityRepository.deleteById(id);
        }

    }

    // DASHBOARD

    @Override
    public List<NonconformityDetailsViewModel> findNonconformities(Principal user) {

        UserEntity userEntity = userRepository.findByUsername(user.getName()).orElseThrow(() ->
                new ObjectNotFoundException("User with username " + user.getName() + " not found!"));

        boolean isAdmin = isAdmin(userEntity);

        List<NonconformityDetailsViewModel> nonconformities = new ArrayList<>();

        if (isAdmin) {
            nonconformities = nonconformityRepository.
                    findAll().
                    stream().
                    map(nonconformityEntity -> {
                      NonconformityDetailsViewModel nonconformityDetailsViewModel = modelMapper.map(nonconformityEntity, NonconformityDetailsViewModel.class);
                      nonconformityDetailsViewModel.setRaisedBy(nonconformityEntity.getRaisedBy().getUsername());

                      return nonconformityDetailsViewModel;
                    }).
                    collect(Collectors.toList());
        } else {
            nonconformities = nonconformityRepository.
                    findByRaisedBy(userEntity).
                    stream().
                    map(nonconformityEntity -> {
                        NonconformityDetailsViewModel nonconformityDetailsViewModel = modelMapper.map(nonconformityEntity, NonconformityDetailsViewModel.class);
                        nonconformityDetailsViewModel.setRaisedBy(nonconformityEntity.getRaisedBy().getUsername());

                        return nonconformityDetailsViewModel;
                    }).
                    collect(Collectors.toList());
        }

        return nonconformities;
    }


}
