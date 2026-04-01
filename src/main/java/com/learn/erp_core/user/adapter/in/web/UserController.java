package com.learn.erp_core.user.adapter.in.web;

import com.learn.erp_core.user.application.dto.user.CreateUserRequest;
import com.learn.erp_core.user.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.application.port.in.user.CreateUserUseCase;
import com.learn.erp_core.user.application.port.in.user.DeleteUserUseCase;
import com.learn.erp_core.user.application.port.in.user.GetUserUseCase;
import com.learn.erp_core.user.application.port.in.user.UpdateUserUseCase;
import com.learn.erp_core.shared.dto.ApiResponse;
import com.learn.erp_core.shared.dto.PaginationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserResponse response = createUserUseCase.createUser(request);
        return new ResponseEntity<>(ApiResponse.success("User created successfully", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        UserResponse response = updateUserUseCase.updateUser(id, request);
        return new ResponseEntity<>(ApiResponse.success("User updated successfully", response), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse response = getUserUseCase.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PaginationResponse<UserResponse>>> getAllUsers(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        PaginationResponse<UserResponse> response = getUserUseCase.getAllUser(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
