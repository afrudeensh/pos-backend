package com.chennai.pos_backend.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Username, email, or phone is required")
    private String identifier;

    @NotBlank(message = "Password is required")
    private String password;
}
