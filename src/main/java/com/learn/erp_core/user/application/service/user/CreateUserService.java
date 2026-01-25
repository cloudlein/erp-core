package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.application.port.in.user.CreateUserUseCase;
import com.learn.erp_core.user.application.port.out.EventPublisher;
import com.learn.erp_core.user.domain.event.UserRegisteredEvent;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateUserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EventPublisher eventPublisher;

    @Transactional
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
             throw new IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
             throw new IllegalArgumentException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(request.getPassword()) 
                .isActive(true)
                .build();
        
        User savedUser = userRepository.save(user);
        
        eventPublisher.publish(new UserRegisteredEvent(savedUser.getId(), savedUser.getEmail()));
        
        return userMapper.toResponse(savedUser);
    }
}
