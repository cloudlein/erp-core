package com.learn.erp_core.auth.adapter.out.persistance.repository;

import com.learn.erp_core.auth.adapter.out.persistance.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {

}

