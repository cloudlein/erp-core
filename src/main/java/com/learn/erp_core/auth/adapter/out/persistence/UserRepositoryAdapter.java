package com.learn.erp_core.auth.adapter.out.persistence;

import com.learn.erp_core.auth.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.auth.adapter.out.persistence.entity.UserEntity;
import com.learn.erp_core.auth.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.auth.adapter.out.persistence.repository.JpaRoleRepository;
import com.learn.erp_core.auth.adapter.out.persistence.repository.JpaUserRepository;
import com.learn.erp_core.auth.domain.model.Role;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User save(User user) {
        // 1. Convert Domain to Entity
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
}

