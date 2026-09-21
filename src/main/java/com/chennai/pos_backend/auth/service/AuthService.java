package com.chennai.pos_backend.auth.service;

import com.chennai.pos_backend.auth.dto.request.ChangePasswordRequest;
import com.chennai.pos_backend.auth.dto.request.LoginRequest;
import com.chennai.pos_backend.auth.dto.request.RegisterRequest;
import com.chennai.pos_backend.auth.dto.response.AuthResponse;
import com.chennai.pos_backend.auth.dto.response.UserResponse;
import com.chennai.pos_backend.auth.enums.Role;

import java.util.List;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getCurrentUser();

    void changePassword(ChangePasswordRequest request);

    List<UserResponse> listUsers(Role role);

    void setUserActive(Long id, boolean active);
}
