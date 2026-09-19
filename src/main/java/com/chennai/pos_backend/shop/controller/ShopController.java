package com.chennai.pos_backend.shop.controller;

import com.chennai.pos_backend.common.response.BaseResponse;
import com.chennai.pos_backend.shop.dto.response.MyShopsResponse;
import com.chennai.pos_backend.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/my-shops")
    public BaseResponse<MyShopsResponse> getMyShops() {

        MyShopsResponse response = shopService.getMyShops();

        return BaseResponse.success(
                response,
                "My shops retrieved successfully"
        );
    }
}