package com.example.scm_system.repository;

import com.example.scm_system.model.entity.RoleEntity;
import com.example.scm_system.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByRole(RoleEnum role);
}
