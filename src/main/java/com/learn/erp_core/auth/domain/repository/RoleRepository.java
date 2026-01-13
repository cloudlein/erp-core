package com.learn.erp_core.auth.domain.repository;

import com.learn.erp_core.auth.domain.model.Role;

import java.util.Optional;

public interface RoleRepository {


    Role save(Role role);

    Optional<Role> findByName(String role);

}

