package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.user.application.port.in.user.DeleteUserUseCase;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsByUserId(userId)){
            throw new IllegalArgumentException("User with id: " + userId +" not found");
        }
        userRepository.delete(userId);
    }
}
