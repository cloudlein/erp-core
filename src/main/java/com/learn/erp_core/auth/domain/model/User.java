package com.learn.erp_core.auth.domain.model;

import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private Boolean isActive;
    private Set<Role> roles;


    public void deactivate() {
        this.isActive = false;
    }

}
