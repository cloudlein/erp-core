package com.learn.erp_core.user.adapter.out.persistence.repository.role;

import com.learn.erp_core.user.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.user.adapter.out.persistence.mapper.RoleMapper;
import com.learn.erp_core.user.domain.model.Role;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleMapper roleMapper;

    @Override
    public Optional<Role> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Role save(Role role) {
        RoleEntity roleEntity = roleMapper.toEntity(role);
        RoleEntity savedEntity = jpaRoleRepository.save(roleEntity);
        return roleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Role> findById(Long roleId) {
        return jpaRoleRepository.findById(roleId)
                .map(roleMapper::toDomain);
    }

    @Override
    public Page<Role> findAllRole(String search, Pageable pageable) {

        Specification<RoleEntity> spec = null;

        if (StringUtils.hasText(search)) {
            String searchPattern = "%" + search.toLowerCase() + "%";
            Specification<RoleEntity> searchSpec = (root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), searchPattern),
                    cb.like(cb.lower(root.get("code")), searchPattern)
            );
            spec = spec == null ? searchSpec : spec.and(searchSpec);
        }


        Page<RoleEntity> page = (spec == null)
                ? jpaRoleRepository.findAll(pageable)
                : jpaRoleRepository.findAll(spec, pageable);

        return page
                .map(roleMapper::toDomain);
    }

    @Override
    public boolean existsById(Long roleId) {
        return jpaRoleRepository.existsById(roleId);
    }

    @Override
    public boolean existsByName(String roleName) {
        return jpaRoleRepository.existsByName(roleName);
    }

    @Override
    public void delete(Long roleId) {
        jpaRoleRepository.deleteById(roleId);
    }
}
