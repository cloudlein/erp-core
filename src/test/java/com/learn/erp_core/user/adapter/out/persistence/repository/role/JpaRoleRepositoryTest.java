package com.learn.erp_core.user.adapter.out.persistence.repository.role;

import com.learn.erp_core.user.adapter.out.persistence.entity.PermissionEntity;
import com.learn.erp_core.user.adapter.out.persistence.entity.RoleEntity;
import com.learn.erp_core.user.adapter.out.persistence.repository.permission.JpaPermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class JpaRoleRepositoryTest {

    @Autowired
    private JpaRoleRepository jpaRoleRepository;

    @Autowired
    private JpaPermissionRepository jpaPermissionRepository;

    @BeforeEach
    void setUp(){

        PermissionEntity permission = PermissionEntity.builder()
                .name("permission")
                .description("description")
                .build();

        jpaPermissionRepository.save(permission);

        RoleEntity role = RoleEntity.builder()
                .name("ROLE_USER")
                .description("ROLE_USER_TEST")
                .permissions(Set.of(permission))
                .build();

        jpaRoleRepository.save(role);
    }

    @Test
    void shouldFindRolesByName(){
        Optional<RoleEntity> foundRole = jpaRoleRepository.findByName("ROLE_USER");

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo("ROLE_USER");
        assertThat(foundRole.get().getDescription()).isEqualTo("ROLE_USER_TEST");
        assertThat(foundRole.get().getPermissions().size()).isEqualTo(1);
    }

}
