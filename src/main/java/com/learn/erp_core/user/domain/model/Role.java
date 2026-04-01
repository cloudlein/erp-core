package com.learn.erp_core.user.domain.model;

import lombok.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Role {
    private Long id;
    private String name;
    private String description;
    private Set<Permission> permissions;
}
