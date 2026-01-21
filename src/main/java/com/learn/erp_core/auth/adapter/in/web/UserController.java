package com.learn.erp_core.auth.adapter.in.web;

import com.learn.erp_core.auth.application.dto.user.CreateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.auth.application.dto.user.UserResponse;
import com.learn.erp_core.auth.application.port.in.user.CreateUserUseCase;
import com.learn.erp_core.auth.application.port.in.user.DeleteUserUseCase;
import com.learn.erp_core.auth.application.port.in.user.GetUserUseCase;
import com.learn.erp_core.auth.application.port.in.user.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        UserResponse response = createUserUseCase.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(Long userId, @RequestBody @Valid UpdateUserRequest request) {
        UserResponse response = updateUserUseCase.updateUser(userId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = getUserUseCase.getUserById(id);
        return ResponseEntity.ok(response);
    }

}
