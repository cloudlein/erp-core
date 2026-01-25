package com.learn.erp_core.user.domain.repository;

import com.learn.erp_core.user.domain.model.Role;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    Role save(Role role);
}
