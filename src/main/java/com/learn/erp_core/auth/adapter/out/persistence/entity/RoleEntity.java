package com.learn.erp_core.auth.adapter.out.persistence.entity;

import com.learn.erp_core.shared.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissions;
}

