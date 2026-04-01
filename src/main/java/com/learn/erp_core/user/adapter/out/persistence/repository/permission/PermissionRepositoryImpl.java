package com.learn.erp_core.user.adapter.out.persistence.repository.permission;

import com.learn.erp_core.user.adapter.out.persistence.entity.PermissionEntity;
import com.learn.erp_core.user.adapter.out.persistence.mapper.PermissionMapper;
import com.learn.erp_core.user.domain.model.Permission;
import com.learn.erp_core.user.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {

    private final JpaPermissionRepository jpaPermissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public Permission save(Permission permission) {

        PermissionEntity permissionEntity = permissionMapper.toEntity(permission);
        PermissionEntity savedPermissionEntity = jpaPermissionRepository.save(permissionEntity);
        return permissionMapper.toDomain(savedPermissionEntity);

    }

    @Override
    public Optional<Permission> findByPermissionId(Long permissionId) {
        return jpaPermissionRepository.findById(permissionId)
                .map(permissionMapper::toDomain);
    }

    @Override
    public Optional<Permission> findByName(String permissionName) {
        return jpaPermissionRepository.findByName(permissionName)
                .map(permissionMapper::toDomain);
    }

    @Override
    public Boolean existsByPermissionId(Long permissionId) {
        return jpaPermissionRepository.existsById(permissionId);
    }

    @Override
    public Boolean existsByName(String permissionName) {
        return jpaPermissionRepository.existsByName(permissionName);
    }

    @Override
    public Page<Permission> findAllPermissions(String search, Pageable pageable) {

        Specification<PermissionEntity> spec = null;

        if (StringUtils.hasText(search)) {
            String searchPattern = "%" + search + "%";
            Specification<PermissionEntity> searchSpec = (((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern)
                    ));

            spec =  spec == null ? searchSpec : spec.and(searchSpec);
        }

        Page<PermissionEntity> page = (spec == null) ?
                jpaPermissionRepository.findAll(pageable)
                : jpaPermissionRepository.findAll(spec, pageable);

        return page.map(permissionMapper::toDomain);
    }

    @Override
    public void delete(Long permissionId) {
        jpaPermissionRepository.deleteById(permissionId);
    }
}
