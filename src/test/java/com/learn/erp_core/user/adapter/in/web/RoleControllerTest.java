package com.learn.erp_core.user.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.erp_core.user.application.dto.role.CreateRoleRequest;
import com.learn.erp_core.user.application.dto.role.RoleResponse;
import com.learn.erp_core.user.application.dto.role.UpdateRoleRequest;
import com.learn.erp_core.user.application.port.in.role.CreateRoleUseCase;
import com.learn.erp_core.user.application.port.in.role.DeleteRoleUseCase;
import com.learn.erp_core.user.application.port.in.role.GetRoleUseCase;
import com.learn.erp_core.user.application.port.in.role.UpdateRoleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new  ObjectMapper();

    @Mock
    private CreateRoleUseCase createRoleUseCase;

    @Mock
    private UpdateRoleUseCase updateRoleUseCase;

    @Mock
    private DeleteRoleUseCase deleteRoleUseCase;

    @Mock
    private GetRoleUseCase getRoleUseCase;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("Should create role and return status 201")
    void createRole_ShouldReturnCreated_WhenRequestIsValid() throws Exception {
        CreateRoleRequest request = CreateRoleRequest.builder()
                .name("test")
                .description("test")
                .permission(Set.of("roletest"))
                .build();

        RoleResponse response = RoleResponse.builder()
                .id(1L)
                .name("test")
                .description("test")
                .build();

        when(createRoleUseCase.createRole(any(CreateRoleRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Role created successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("Should update role and return status 200")
    void updateRole_ShouldReturnOk_WhenRequestIsValid() throws Exception {
        // Arrange
        Long roleId = 1L;
        UpdateRoleRequest request = UpdateRoleRequest.builder()
                .name("testroleupdate")
                .description("test")
                .permission(Set.of("roletest"))
                .build();

        RoleResponse response = RoleResponse.builder()
                .id(1L)
                .name("testroleupdate")
                .description("test")
                .build();


        when(updateRoleUseCase.updateRole(eq(roleId), any(UpdateRoleRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/v1/roles/{id}", roleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Role updated successfully"))
                .andExpect(jsonPath("$.data.name").value("testroleupdate"));
    }

    @Test
    @DisplayName("Should get role by id and return status 200")
    void getRoleById_ShouldReturnOk_WhenRoleExists() throws Exception {
        // Arrange
        Long roleId = 1L;
        RoleResponse response = RoleResponse.builder()
                .id(roleId)
                .name("testroleupdate")
                .description("test")
                .build();


        when(getRoleUseCase.getByRoleId(roleId)).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/v1/roles/{id}", roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(roleId));
    }


    @Test
    @DisplayName("Should delete role when role exists and return 200")
    void deleteUser_ShouldReturnOk_WhenUserExists() throws Exception {
        // Arrange
        Long roleId = 1L;
        doNothing().when(deleteRoleUseCase).deleteRole(roleId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/roles/{id}", roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Role deleted successfully"));

        verify(deleteRoleUseCase).deleteRole(roleId);
    }
}
