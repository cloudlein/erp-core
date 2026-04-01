package com.learn.erp_core.user.application.port.in.user;

import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import org.springframework.data.domain.Pageable;

public interface GetUserUseCase {
    UserResponse getUserById(Long userId);
    PaginationResponse<UserResponse> getAllUser(String search, Pageable pageable);
}
