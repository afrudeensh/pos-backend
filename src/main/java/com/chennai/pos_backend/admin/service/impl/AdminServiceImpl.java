package com.chennai.pos_backend.admin.service.impl;

import com.chennai.pos_backend.admin.dto.AdminOwnerShopsResponse;
import com.chennai.pos_backend.admin.service.AdminService;
import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.auth.enums.Role;
import com.chennai.pos_backend.auth.repository.UserRepository;
import com.chennai.pos_backend.common.security.CurrentUser;
import com.chennai.pos_backend.shop.entity.Shop;
import com.chennai.pos_backend.shop.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @Override
    public List<AdminOwnerShopsResponse> getAllOwnersWithShops() {

        List<User> owners = userRepository.findByRole(Role.SHOP_OWNER);

        return owners.stream()
                .map(owner -> {

                    List<Shop> shops =
                            shopRepository.findByOwnerUserId(owner.getId());

                    return AdminOwnerShopsResponse.from(owner, shops);
                })
                .toList(); // Converts Stream into List
    }
}
