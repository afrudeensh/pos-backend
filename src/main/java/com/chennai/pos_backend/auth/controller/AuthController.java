package com.chennai.pos_backend.auth.controller;

import com.chennai.pos_backend.auth.dto.request.ChangePasswordRequest;
import com.chennai.pos_backend.auth.dto.request.LoginRequest;
import com.chennai.pos_backend.auth.dto.request.RegisterRequest;
import com.chennai.pos_backend.auth.dto.response.AuthResponse;
import com.chennai.pos_backend.auth.dto.response.UserResponse;
import com.chennai.pos_backend.auth.enums.Role;
import com.chennai.pos_backend.auth.service.AuthService;
import com.chennai.pos_backend.common.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public BaseResponse<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        AuthResponse response = authService.register(request);

        return BaseResponse.created(
                response,
                "Registration successful"
        );
    }

    @PostMapping("/login")
    public BaseResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {

        AuthResponse response = authService.login(request);

        return BaseResponse.success(
                response,
                "Login successful"
        );
    }

    @GetMapping("/me")
    public BaseResponse<UserResponse> getCurrentUser() {
        UserResponse response = authService.getCurrentUser();
        return BaseResponse.success(response, "User fetched");
    }

    @PostMapping("/change-password")
    public BaseResponse<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        authService.changePassword(request);
        return BaseResponse.success(null, "Password changed successfully");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<List<UserResponse>> listUsers(
            @RequestParam(required = false) Role role
    ) {
        List<UserResponse> response = authService.listUsers(role);
        return BaseResponse.success(response, "Users fetched");
    }

    @PatchMapping("/users/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<Void> toggleUserStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {
        authService.setUserActive(id, active);
        return BaseResponse.success(null, "User status updated");
    }

}