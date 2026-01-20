package com.learn.erp_core.auth.adapter.out.persistence.repository.user;

import com.learn.erp_core.auth.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.auth.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.auth.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.auth.adapter.out.persistence.repository.role.JpaRoleRepository;
import com.learn.erp_core.auth.domain.model.Role;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<RoleEntity> managedRoles = new HashSet<>();
            for (Role role : user.getRoles()) {
                RoleEntity roleEntity = jpaRoleRepository.findByName(role.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName()));
                managedRoles.add(roleEntity);
            }
            userEntity.setRoles(managedRoles);
        }

        UserEntity savedEntity = jpaUserRepository.save(userEntity);

        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return jpaUserRepository.findById(userId)
                .map(userMapper::toDomain);
    }

    @Override
    public Page<User> findAllUser(String search, Pageable pageable) {
        Specification<UserEntity> spec = null;

        if (StringUtils.hasText(search)){
            String searchPattern = "%" + search.toLowerCase() + "%";
            Specification<UserEntity> searchSpec = ((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchPattern)
            ));

            spec = spec == null ? searchSpec : spec.and(searchSpec);
        }

        Page<UserEntity> page = (spec == null)
                ? jpaUserRepository.findAll(pageable)
                : jpaUserRepository.findAll(spec, pageable);

        return page.map(userMapper::toDomain);
    }
}

