package com.chennai.pos_backend.admin.service;

import com.chennai.pos_backend.admin.dto.AdminOwnerShopsResponse;

import java.util.List;

public interface AdminService {

    List<AdminOwnerShopsResponse> getAllOwnersWithShops();
}
