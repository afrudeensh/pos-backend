package com.chennai.pos_backend.shop.service.Impl;

import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.repository.UserRepository;
import com.chennai.pos_backend.common.exception.ApiException;
import com.chennai.pos_backend.common.security.CurrentUser;

import com.chennai.pos_backend.shop.dto.response.MyShopsResponse;
import com.chennai.pos_backend.shop.entity.Shop;
import com.chennai.pos_backend.shop.repository.ShopRepository;
import com.chennai.pos_backend.shop.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @Override
    public MyShopsResponse getMyShops() {

        // 1. Get logged-in user ID from JWT
        Long userId = currentUser.getUserId();

        // 2. Get owner
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(
                        "User not found",
                        HttpStatus.NOT_FOUND
                ));

        // 3. Get shops owned by this user
        List<Shop> shops =
                shopRepository.findByOwnerUserId(userId);

        // 4. Return owner + shops
        return MyShopsResponse.from(
                user,
                shops
        );
    }
}
