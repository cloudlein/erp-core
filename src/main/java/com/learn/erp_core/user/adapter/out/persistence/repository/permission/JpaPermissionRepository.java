package com.learn.erp_core.user.adapter.out.persistence.repository.permission;

import com.learn.erp_core.user.adapter.out.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, Long> {
}
