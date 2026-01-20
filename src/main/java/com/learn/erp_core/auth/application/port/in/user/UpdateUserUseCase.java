package com.learn.erp_core.auth.application.port.in.user;

import com.learn.erp_core.auth.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;

public interface UpdateUserUseCase {
    UserResponse updateUser(Long userId, UpdateUserRequest request);
}
