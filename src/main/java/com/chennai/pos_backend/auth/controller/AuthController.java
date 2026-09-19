package com.chennai.pos_backend.auth.controller;

import com.chennai.pos_backend.auth.dto.request.LoginRequest;
import com.chennai.pos_backend.auth.dto.request.RegisterRequest;
import com.chennai.pos_backend.auth.dto.response.AuthResponse;
import com.chennai.pos_backend.auth.service.AuthService;
import com.chennai.pos_backend.common.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}