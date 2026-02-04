package com.learn.erp_core.user.adapter.out.persistence.entity;

import com.learn.erp_core.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String name;
    
    private String description;
}
