package com.learn.erp_core.user.adapter.out.persistence.repository.permission;

import com.learn.erp_core.user.adapter.out.persistence.entity.PermissionEntity;
import com.learn.erp_core.user.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, Long> , JpaSpecificationExecutor<PermissionEntity> {

    Optional<PermissionEntity> findByName(String name);
    Boolean existsByName(String name);

}
