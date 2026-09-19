package com.chennai.pos_backend.shop.repository;

import com.chennai.pos_backend.shop.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    List<Shop> findByOwnerUserId(Long ownerUserId);

    Optional<Shop> findByIdAndOwnerUserId(Long id, Long ownerUserId);
}
