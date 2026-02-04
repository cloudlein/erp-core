package com.learn.erp_core.user.application.service.permission;

import com.learn.erp_core.shared.dto.OptionResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.shared.util.PaginationAssembler;
import com.learn.erp_core.user.adapter.out.persistence.mapper.PermissionMapper;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.port.in.permission.GetPermissionUseCase;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetPermissionService implements GetPermissionUseCase {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Transactional(readOnly = true)
    @Override
    public PermissionResponse getPermissionById(Long permissionId) {
        return permissionRepository.findByPermissionId(permissionId)
                .map(permissionMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Permission Not Found with id: " + permissionId));
    }

    @Override
    public PaginationResponse<PermissionResponse> getAllPermissions(String search, Pageable pageable) {
        Page<Permission> page = permissionRepository.findAllPermissions(search, pageable);
        List<PermissionResponse> content = page.getContent().stream()
                .map(permissionMapper::toResponse)
                .toList();
        return PaginationAssembler.from(page, content);
    }

    @Override
    public List<OptionResponse> getOptions() {
        return permissionRepository.findAllPermissions(null, Pageable.unpaged())
                .stream()
                .map(p -> OptionResponse.builder()
                        .value(p.getId())
                        .label(p.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
