package com.learn.erp_core.user.application.service.role;


import com.learn.erp_core.user.application.port.in.role.DeleteRoleUseCase;
import com.learn.erp_core.user.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeleteRoleService implements DeleteRoleUseCase {

    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)){
            throw new IllegalArgumentException("Role not found with id : " + roleId);
        }

        roleRepository.delete(roleId);
    }
}
