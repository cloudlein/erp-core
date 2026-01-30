package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.shared.util.PaginationAssembler;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.application.port.in.user.GetUserUseCase;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetUserService implements GetUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id : " + userId));
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<UserResponse> getAllUser(String search, Pageable pageable) {
        Page<User> page = userRepository.findAllUser(search, pageable);
        List<UserResponse> content =  page.getContent().stream()
                .map(userMapper::toResponse)
                .toList();
        return PaginationAssembler.from(page, content);
    }
}
