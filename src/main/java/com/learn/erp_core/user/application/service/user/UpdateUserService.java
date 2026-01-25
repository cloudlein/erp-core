package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.user.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.application.port.in.user.UpdateUserUseCase;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateUserService implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
             if(userRepository.existsByEmail(request.getEmail())) {
                  throw new IllegalArgumentException("Email already exists");
             }
             user = user.toBuilder().email(request.getEmail()).build();
        }
        
        if(request.getIsActive() != null) {
            user = user.toBuilder().isActive(request.getIsActive()).build();
        }
        
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
