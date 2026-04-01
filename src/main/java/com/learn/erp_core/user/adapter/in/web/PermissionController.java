package com.learn.erp_core.user.adapter.in.web;

import com.learn.erp_core.shared.dto.ApiResponse;
import com.learn.erp_core.shared.dto.OptionResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import com.learn.erp_core.user.application.dto.permission.CreatePermissionRequest;
import com.learn.erp_core.user.application.dto.permission.PermissionResponse;
import com.learn.erp_core.user.application.dto.permission.UpdatePermissionRequest;
import com.learn.erp_core.user.application.port.in.permission.CreatePermissionUseCase;
import com.learn.erp_core.user.application.port.in.permission.DeletePermissionUseCase;
import com.learn.erp_core.user.application.port.in.permission.GetPermissionUseCase;
import com.learn.erp_core.user.application.port.in.permission.UpdatePermissionUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final GetPermissionUseCase getPermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody @Valid CreatePermissionRequest request
            ) {
        PermissionResponse response = createPermissionUseCase.createPermission(request);
        return new ResponseEntity<>(ApiResponse.success("Permission created successfully", response), HttpStatus.OK);
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable Long permissionId, @RequestBody @Valid UpdatePermissionRequest request
            ) {
        PermissionResponse response = updatePermissionUseCase.updatePermission(permissionId, request);
        return new ResponseEntity<>(ApiResponse.success("Permission updated successfully", response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<PermissionResponse>>> getAllPermission(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        PaginationResponse<PermissionResponse> response = getPermissionUseCase.getAllPermissions(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/options")
    public ResponseEntity<ApiResponse<List<OptionResponse>>> getPermissionOptions() {
        List<OptionResponse> responses = getPermissionUseCase.getOptions();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

}
