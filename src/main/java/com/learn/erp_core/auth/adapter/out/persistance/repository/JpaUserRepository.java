package com.learn.erp_core.auth.adapter.out.persistance.repository;

import com.learn.erp_core.auth.adapter.out.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

}

