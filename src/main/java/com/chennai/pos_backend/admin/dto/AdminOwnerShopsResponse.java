package com.chennai.pos_backend.admin.dto;

import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.enums.Role;
import com.chennai.pos_backend.shop.dto.response.ShopResponse;
import com.chennai.pos_backend.shop.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminOwnerShopsResponse {

    private Long userId;
    private String username;
    private String name;
    private String email;
    private String ownerPhoneNo;
    private Role role;
    private List<ShopResponse> shops;

    public static AdminOwnerShopsResponse from(
            User user,
            List<Shop> shops
    ) {

        return new AdminOwnerShopsResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                shops.stream()
                        .map(ShopResponse::from)
                        .toList()
        );
    }
}