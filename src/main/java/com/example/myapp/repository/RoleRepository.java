package com.example.myapp.repository;

import com.example.myapp.entity.RoleEntity;
import com.example.myapp.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByTitle(RoleEnum title);
}
