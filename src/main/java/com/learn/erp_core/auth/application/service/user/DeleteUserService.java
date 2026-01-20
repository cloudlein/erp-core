package com.learn.erp_core.auth.application.service.user;

import com.learn.erp_core.auth.application.port.in.user.DeleteUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteUserService implements DeleteUserUseCase {
    @Override
    public void deleteUser(Long userId) {
        // TODO: Implement Logic
    }
}
