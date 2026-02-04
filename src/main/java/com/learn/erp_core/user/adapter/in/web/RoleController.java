package com.learn.erp_core.user.adapter.in.web;

import com.learn.erp_core.shared.dto.ApiResponse;
import com.learn.erp_core.shared.dto.OptionResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.user.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.dto.role.UpdateRoleRequest;
import com.learn.erp_core.user.application.port.in.role.CreateRoleUseCase;
import com.learn.erp_core.user.application.port.in.role.DeleteRoleUseCase;
import com.learn.erp_core.user.application.port.in.role.GetRoleUseCase;
import com.learn.erp_core.user.application.port.in.role.UpdateRoleUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final GetRoleUseCase getRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;


    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody @Valid CreateRoleRequest request) {
        RoleResponse roleResponse = createRoleUseCase.createRole(request);
        return new ResponseEntity<>(ApiResponse.success("Role created successfully", roleResponse), HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable Long roleId, @RequestBody @Valid UpdateRoleRequest request) {
        RoleResponse roleResponse = updateRoleUseCase.updateRole(roleId, request);
        return new ResponseEntity<>(ApiResponse.success("Role updated successfully", roleResponse), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable Long roleId) {
        RoleResponse response = getRoleUseCase.getByRoleId(roleId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> deleteRoleById(@PathVariable Long roleId) {
        deleteRoleUseCase.deleteRole(roleId);
        return ResponseEntity.ok(ApiResponse.success("Role deleted successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<RoleResponse>>> getAllRoles(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        PaginationResponse<RoleResponse> response = getRoleUseCase.getAllRole(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/options")
    public ResponseEntity<ApiResponse<List<OptionResponse>>> getRoleOptions() {
        List<OptionResponse> responses = getRoleUseCase.getOptions();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
