package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.dto.role.UpdateRoleRequest;
import com.learn.erp_core.user.application.port.in.role.UpdateRoleUseCase;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateRoleService implements UpdateRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse updateRole(Long roleId, UpdateRoleRequest request) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));

        if (request.getName() != null && !request.getName().equals(role.getName())) {
            if (roleRepository.existsByName(request.getName())) {
                throw new IllegalArgumentException("role already exists");
            }

            role = role.toBuilder().name(request.getName()).build();
        }

        return null;
    }
}
