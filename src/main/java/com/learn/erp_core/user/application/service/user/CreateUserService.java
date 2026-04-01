package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.application.port.in.user.CreateUserUseCase;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Attempt to create user with existing username {}", request.getUsername());
            throw new ConflictException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Attempt to create user with existing email {}", request.getEmail());
            throw new ConflictException("Email already exists");
        }

        log.info("Creating user with username {}", request.getUsername());
        User user = userMapper.toDomain(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
