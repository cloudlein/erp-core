package com.learn.erp_core.user.application.service.user;

import com.learn.erp_core.shared.exception.ConflictException;
import com.learn.erp_core.shared.exception.ResourceNotFoundException;
import com.learn.erp_core.user.adapter.out.persistence.mapper.UserMapper;
import com.learn.erp_core.user.application.dto.user.UpdateUserRequest;
import com.learn.erp_core.user.application.dto.user.UserResponse;
import com.learn.erp_core.user.domain.model.User;
import com.learn.erp_core.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Contoh Unit Test untuk UpdateUserService.
 * 
 * Menggunakan JUnit 5 dan Mockito.
 * Penjelasan anotasi:
 * - @ExtendWith(MockitoExtension.class): Mengintegrasikan Mockito dengan JUnit 5.
 * - @Mock: Membuat object tiruan (mock) dari dependency.
 * - @InjectMocks: Menyuntikkan mock ke dalam class yang sedang di-test.
 * - @Test: Menandai method sebagai test case.
 * - @DisplayName: Memberikan nama deskriptif untuk test case (opsional tapi bagus untuk dokumentasi).
 */
@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

    // Dependency yang akan di-mock (ditiru perilakunya)
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    // Class yang akan di-test (System Under Test)
    // Mockito akan otomatis memasukkan userRepository dan userMapper ke sini
    @InjectMocks
    private UpdateUserService updateUserService;

    @Test
    @DisplayName("Should update user successfully when data is valid")
    void updateUser_ShouldReturnUpdatedUser_WhenDataIsValid() {
        // 1. Arrange (Persiapan Data)
        Long userId = 1L;
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";
        
        // Request update
        UpdateUserRequest request = UpdateUserRequest.builder()
                .email(newEmail)
                .isActive(true)
                .build();

        // Data user yang ada di database (sebelum update)
        User existingUser = User.builder()
                .id(userId)
                .username("user1")
                .email(oldEmail)
                .isActive(false)
                .build();

        // Data user setelah update (yang diharapkan disimpan)
        // Kita gunakan any() di stubbing karena object instance mungkin berbeda
        User updatedUser = existingUser.toBuilder()
                .email(newEmail)
                .isActive(true)
                .build();
        
        // Response yang diharapkan
        UserResponse expectedResponse = UserResponse.builder()
                .id(userId)
                .username("user1")
                .email(newEmail)
                .isActive(true)
                .build();

        // Mengatur perilaku Mock
        // Ketika mencari user by ID, kembalikan user yang ada
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(existingUser));
        
        // Ketika cek email baru, kembalikan false (email belum dipakai orang lain)
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);
        
        // Ketika simpan user, kembalikan user yang sudah diupdate
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        
        // Ketika mapping ke response, kembalikan response yang diharapkan
        when(userMapper.toResponse(any(User.class))).thenReturn(expectedResponse);

        // 2. Act (Eksekusi Method yang ditest)
        UserResponse actualResponse = updateUserService.updateUser(userId, request);

        // 3. Assert (Verifikasi Hasil)
        assertNotNull(actualResponse);
        assertEquals(newEmail, actualResponse.getEmail());
        assertTrue(actualResponse.getIsActive());

        // Verifikasi bahwa method-method dependency dipanggil dengan benar
        verify(userRepository).findByUserId(userId);
        verify(userRepository).existsByEmail(newEmail);
        
        // Gunakan ArgumentCaptor untuk memverifikasi object yang dikirim ke method save
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertEquals(newEmail, capturedUser.getEmail());
        assertTrue(capturedUser.getIsActive());
        
        verify(userMapper).toResponse(any(User.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user is not found")
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        UpdateUserRequest request = UpdateUserRequest.builder().build();

        // Mock repository untuk mengembalikan Optional kosong
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        // Memastikan exception dilempar saat method dipanggil
        assertThrows(ResourceNotFoundException.class, () -> {
            updateUserService.updateUser(userId, request);
        });

        // Verifikasi save TIDAK PERNAH dipanggil
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ConflictException when new email is already used by another user")
    void updateUser_ShouldThrowConflictException_WhenEmailAlreadyExists() {
        // Arrange
        Long userId = 1L;
        String oldEmail = "me@example.com";
        String existingEmail = "other@example.com"; // Email milik orang lain

        UpdateUserRequest request = UpdateUserRequest.builder()
                .email(existingEmail)
                .build();

        User existingUser = User.builder()
                .id(userId)
                .email(oldEmail)
                .build();

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(existingUser));
        // Mock bahwa email sudah ada di DB
        when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> {
            updateUserService.updateUser(userId, request);
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not update email when request email is same as old email")
    void updateUser_ShouldNotCheckEmailExistence_WhenEmailIsSame() {
        // Arrange
        Long userId = 1L;
        String email = "same@example.com";

        UpdateUserRequest request = UpdateUserRequest.builder()
                .email(email) // Email sama
                .isActive(true)
                .build();

        User existingUser = User.builder()
                .id(userId)
                .email(email)
                .isActive(false)
                .build();

        User updatedUser = existingUser.toBuilder()
                .isActive(true)
                .build();
        
        UserResponse expectedResponse = UserResponse.builder().build();

        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        when(userMapper.toResponse(any(User.class))).thenReturn(expectedResponse);

        // Act
        updateUserService.updateUser(userId, request);

        // Assert
        // Pastikan existsByEmail TIDAK dipanggil karena email sama
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository).save(any(User.class));
    }
}
