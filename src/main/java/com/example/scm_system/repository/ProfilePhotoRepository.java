package com.example.scm_system.repository;

import com.example.scm_system.model.entity.ProfilePhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePhotoRepository extends JpaRepository<ProfilePhotoEntity, Long> {

    ProfilePhotoEntity findByUrl(String url);
}
