package com.chennai.pos_backend.auth.service.impl;

import com.chennai.pos_backend.auth.dto.request.LoginRequest;
import com.chennai.pos_backend.auth.dto.request.RegisterRequest;
import com.chennai.pos_backend.auth.dto.response.AuthResponse;
import com.chennai.pos_backend.auth.dto.response.UserResponse;
import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.enums.Role;
import com.chennai.pos_backend.auth.repository.UserRepository;
import com.chennai.pos_backend.auth.service.AuthService;
import com.chennai.pos_backend.common.exception.ApiException;
import com.chennai.pos_backend.common.security.CurrentUser;
import com.chennai.pos_backend.common.security.JwtUtil;
import com.chennai.pos_backend.shop.entity.Shop;
import com.chennai.pos_backend.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {

        // 1. Check username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(
                    "Username already registered",
                    HttpStatus.CONFLICT
            );
        }

        // 2. Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(
                    "Email already registered",
                    HttpStatus.CONFLICT
            );
        }

        // 3. Check phone
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new ApiException(
                    "Phone number already registered",
                    HttpStatus.CONFLICT
            );
        }

        // 4. Create User
        User user = new User();

        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // Never store plain password
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );

        // Public registration can only create SHOP_OWNER
        user.setRole(Role.SHOP_OWNER);

        user.setActive(true);

        // 5. Save User
        User savedUser = userRepository.save(user);

        // 6. Create first Shop
        Shop shop = new Shop();

        shop.setOwnerUser(savedUser);
        shop.setShopName(request.getShopName());
        shop.setAddress(request.getShopAddress());
        shop.setPhone(request.getShopPhone());
        shop.setEmail(request.getShopEmail());
        shop.setGstNumber(request.getGstNumber());
        shop.setActive(true);

        // 7. Save Shop
        shopRepository.save(shop);

        // 8. Generate JWT
        String token = jwtUtil.generateToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );

        // 9. Return authentication response
        return new AuthResponse(
                token,
                UserResponse.from(savedUser)
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        // 1. Find user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(
                        "Invalid email or password",
                        HttpStatus.UNAUTHORIZED
                ));

        // 2. Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )) {
            throw new ApiException(
                    "Invalid email or password",
                    HttpStatus.UNAUTHORIZED
            );
        }

        // 3. Check account status
        if (!user.isActive()) {
            throw new ApiException(
                    "Account is not active",
                    HttpStatus.FORBIDDEN
            );
        }

        // 4. Generate JWT
        String token = jwtUtil.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );

        // 5. Return response
        return new AuthResponse(
                token,
                UserResponse.from(user)
        );
    }
}
