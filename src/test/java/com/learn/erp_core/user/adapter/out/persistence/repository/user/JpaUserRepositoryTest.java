package com.learn.erp_core.user.adapter.out.persistence.repository.user;

import com.learn.erp_core.user.adapter.out.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @BeforeEach
    void setUp() {
        UserEntity user = new UserEntity();
        user.setUsername("realuser");
        user.setEmail("real@example.com");
        user.setPasswordHash("hashedpassword");
        user.setIsActive(true);

        jpaUserRepository.save(user);
    }

    @Test
    void shouldFindUserByUsername() {
        Optional<UserEntity> foundUser = jpaUserRepository.findByUsername("realuser");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("realuser");
        assertThat(foundUser.get().getEmail()).isEqualTo("real@example.com");
    }
}
