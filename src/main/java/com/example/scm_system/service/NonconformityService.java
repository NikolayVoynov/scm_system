package com.example.scm_system.service;

import com.example.scm_system.model.binding.NonconformityAddBindingModel;
import com.example.scm_system.model.service.NonconformityAddServiceModel;
import com.example.scm_system.model.service.NonconformityUpdateServiceModel;
import com.example.scm_system.model.view.NonconformityDetailsViewModel;

public interface NonconformityService {
    NonconformityAddServiceModel addNonconformity(NonconformityAddBindingModel nonconformityAddBindingModel, String ownerUsername);

    NonconformityDetailsViewModel findById(Long id, String currentUser);

    boolean isOwner(String username, Long id);

    void updateNonconformity(NonconformityUpdateServiceModel nonconformityUpdateServiceModel);

    void deleteNonconformity(Long id);
}
