package com.example.scm_system.model.entity;

import com.example.scm_system.model.entity.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity{

    private RoleEnum role;

    public RoleEntity() {
    }

    @Enumerated(EnumType.STRING)
    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum name) {
        this.role = name;
    }
}
