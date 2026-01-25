package com.learn.erp_core.user.domain.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {
    private Long id;
    private String name;
    private String description;
}
