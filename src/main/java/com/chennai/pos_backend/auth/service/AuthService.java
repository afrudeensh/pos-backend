package com.chennai.pos_backend.auth.service;

import com.chennai.pos_backend.auth.dto.request.LoginRequest;
import com.chennai.pos_backend.auth.dto.request.RegisterRequest;
import com.chennai.pos_backend.auth.dto.response.AuthResponse;
import com.chennai.pos_backend.auth.dto.response.UserResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
