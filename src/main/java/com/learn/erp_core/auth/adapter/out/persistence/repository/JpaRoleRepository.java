package com.learn.erp_core.auth.adapter.out.persistence.repository;

import com.learn.erp_core.auth.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.auth.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);

}


