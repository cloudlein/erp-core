package com.learn.erp_core.user.domain.repository;

import com.learn.erp_core.user.domain.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PermissionRepository {

    Permission save(Permission permission);
    Optional<Permission> findByPermissionId(Long permissionId);
    Optional<Permission> findByName(String permissionName);
    Boolean existsByPermissionId(Long permissionId);
    Boolean existsByName(String permissionName);

    Page<Permission> findAllPermissions(String search, Pageable pageable);

    void delete(Long permissionId);

}
