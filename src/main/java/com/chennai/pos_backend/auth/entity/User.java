package com.chennai.pos_backend.auth.entity;

import com.chennai.pos_backend.auth.enums.Role;
import com.chennai.pos_backend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * User Entity
 *
 * A user is no longer tied to a single shop. Ownership of shops is expressed
 * from the Shop side via Shop.ownerUser (shops.owner_user_id) — one user can
 * own zero, one, or many shops; an ADMIN owns none.
 *
 * Extends BaseEntity to inherit: id, createdAt, updatedAt.
 * Maps to the "users" database table.
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_username",
                        columnNames = "username"
                )
        }
)
@Getter
@Setter
public class User extends BaseEntity {

    /** Unique username used for login (platform-wide, not per-shop). */
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    /** BCrypt password hash. Never store or return plain-text passwords. */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /** ADMIN or SHOP_OWNER. Stored as a String in the database. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    /** User's full name. */
    @Column(name = "full_name")
    private String fullName;

    /** User's phone number. */
    private String phone;

    /**
     * User active status. true = Active, false = Disabled.
     * Login and authorization checks must be enforced in the backend.
     */
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}