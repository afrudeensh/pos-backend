package com.chennai.pos_backend.shop.dto.response;

import com.chennai.pos_backend.shop.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopResponse {

    private Long id;
    private String shopName;
    private String address;
    private String phone;
    private String email;
    private String gstNumber;
    private boolean active;

    public static ShopResponse from(Shop shop) {
        return new ShopResponse(
                shop.getId(),
                shop.getShopName(),
                shop.getAddress(),
                shop.getPhone(),
                shop.getEmail(),
                shop.getGstNumber(),
                shop.isActive()
        );
    }
}