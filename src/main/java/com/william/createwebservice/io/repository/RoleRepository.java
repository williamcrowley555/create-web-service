package com.william.createwebservice.io.repository;

import com.william.createwebservice.io.entity.RoleEntity;
import com.william.createwebservice.ui.model.response.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(Role name);
}
