package com.chennai.pos_backend.product.entity;

import com.chennai.pos_backend.category.entity.Category;
import com.chennai.pos_backend.common.BaseEntity;
import com.chennai.pos_backend.shop.entity.Shop;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Product Entity
 *
 * Represents a product belonging to a specific shop
 * and category.
 *
 * Extends BaseEntity to inherit:
 * - id
 * - createdAt
 * - updatedAt
 */
@Entity
@Table(
        name = "products",
        indexes = {
                @Index(
                        name = "idx_products_shop_category",
                        columnList = "shop_id, category_id"
                ),
                @Index(
                        name = "idx_products_shop_name_active",
                        columnList = "shop_id, name, is_active"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    /**
     * Shop to which this product belongs.
     *
     * Many products can belong to one shop.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    /**
     * Category to which this product belongs.
     *
     * Many products can belong to one category.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Product name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Product brand.
     */
    private String brand;

    /**
     * Selling price of the product.
     *
     * DECIMAL(12,2)
     */
    @Column(
            name = "selling_price",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal sellingPrice;

    /**
     * GST percentage applicable to the product.
     *
     * DECIMAL(5,2)
     */
    @Column(
            name = "gst_percentage",
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal gstPercentage;

    /**
     * Unit in which the product is sold.
     *
     * Examples:
     * PCS, LITRE, KG, BOX
     */
    @Column(nullable = false, length = 20)
    private String unit;

    /**
     * Current available stock.
     *
     * DECIMAL(12,3) allows fractional quantities.
     *
     * Example:
     * 10.000 PCS
     * 5.500 LITRE
     */
    @Column(
            name = "current_stock",
            nullable = false,
            precision = 12,
            scale = 3
    )
    private BigDecimal currentStock;

    /**
     * Product active status.
     *
     * true  = Active
     * false = Inactive
     */
    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}