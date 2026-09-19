package com.chennai.pos_backend.category.entity;

import com.chennai.pos_backend.common.BaseEntity;
import com.chennai.pos_backend.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "categories",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_categories_shop_name", columnNames = {"shop_id", "name"}
        ),
        indexes = @Index(name = "idx_categories_shop_active", columnList = "shop_id,is_active")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(nullable = false)
    private String name;

    private String description;

    // Soft delete: existing products keep their historical reference; hidden from new-product forms.
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}