package com.chennai.pos_backend.shop.entity;

import com.chennai.pos_backend.auth.entity.User;
import com.chennai.pos_backend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Shop Entity
 *
 * ownerUser (shops.owner_user_id) replaces the old users.shop_id column.
 * Many shops can point at the same owner: one user, many shops (e.g. a main
 * shop plus several rented-out ones). Each shop still has exactly one owner.
 */
@Entity
@Table(
        name = "shops",
        indexes = @Index(name = "idx_shops_owner_user", columnList = "owner_user_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User ownerUser;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    private String address;

    private String phone;

    private String email;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}