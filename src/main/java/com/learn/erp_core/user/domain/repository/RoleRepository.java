package com.learn.erp_core.user.domain.repository;

import com.learn.erp_core.user.domain.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    Role save(Role role);

    Optional<Role> findById(Long roleId);

    Page<Role>findAllRole(String search, Pageable pageable);

    boolean existsById(Long roleId);

    boolean existsByName(String roleName);

    void delete(Long roleId);

}
