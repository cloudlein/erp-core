package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.port.in.role.CreateRoleUseCase;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public RoleResponse createRole(CreateRoleRequest request) {

        if (roleRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Role already exists");
        }

        Role role = roleMapper.toDomain(request);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponse(savedRole);
    }


}
