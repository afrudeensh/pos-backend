package com.chennai.pos_backend.admin.controller;

import com.chennai.pos_backend.admin.dto.AdminOwnerShopsResponse;
import com.chennai.pos_backend.admin.service.AdminService;
import com.chennai.pos_backend.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {

    private final AdminService adminService;

    @GetMapping
    public BaseResponse<List<AdminOwnerShopsResponse>> getAllOwnersWithShops() {

        List<AdminOwnerShopsResponse> response =
                adminService.getAllOwnersWithShops();

        return BaseResponse.success(
                response,
                "All shop owners and their shops retrieved successfully"
        );
    }
}