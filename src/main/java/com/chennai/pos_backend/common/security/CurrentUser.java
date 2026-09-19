package com.chennai.pos_backend.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// Small helper to read the authenticated user's id out of the SecurityContext
// (JwtAuthFilter stores the userId as the Authentication principal).
@Component
public class CurrentUser {
    public Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
