package com.learn.erp_core.user.application.service.role;

import com.learn.erp_core.shared.dto.OptionResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.shared.util.PaginationAssembler;
import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.port.in.role.GetRoleUseCase;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRoleService implements GetRoleUseCase {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse getByRoleId(Long roleId) {
        return roleRepository.findById(roleId)
                .map(roleMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
    }

    @Override
    public PaginationResponse<RoleResponse> getAllRole(String search, Pageable pageable) {
        Page<Role> page = roleRepository.findAllRole(search, pageable);
        List<RoleResponse> responses = page.getContent().stream()
                .map(roleMapper::toResponse)
                .toList();
        return PaginationAssembler.from(page, responses);
    }

    @Override
    public List<OptionResponse> getOptions() {
        return roleRepository.findAllRole(null, Pageable.unpaged())
                .stream()
                .map( r -> OptionResponse.builder()
                        .value(r.getId())
                        .label(r.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
