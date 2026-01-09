package com.learn.erp_core.auth.adapter.out.persistance.repository.adapter;

import com.learn.erp_core.auth.adapter.out.persistance.repository.JpaUserRepository;
import com.learn.erp_core.auth.domain.model.User;
import com.learn.erp_core.auth.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }
}
