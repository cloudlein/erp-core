package com.learn.erp_core.user.application.port.in.user;

import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;

public interface CreateUserUseCase {
    UserResponse createUser(CreateUserRequest request);
}
