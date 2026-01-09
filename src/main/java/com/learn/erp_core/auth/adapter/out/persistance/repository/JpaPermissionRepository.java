package com.learn.erp_core.auth.adapter.out.persistance.repository;

import com.learn.erp_core.auth.adapter.out.persistance.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, Long> {

}

