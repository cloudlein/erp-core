package com.learn.erp_core.auth.application.service.user;

import com.learn.erp_core.auth.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.auth.application.dto.user.UserResponse;
import com.learn.erp_core.auth.application.port.in.user.GetUserUseCase;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetUserService implements GetUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found with user id : " + userId));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUser(String search, Pageable pageable) {
        Page<User> users = userRepository.findAllUser(search, pageable);
        List<UserResponse> responses = users.getContent().stream()
                .map(userMapper::toResponse)
                .toList();
        return responses;
    }
}
