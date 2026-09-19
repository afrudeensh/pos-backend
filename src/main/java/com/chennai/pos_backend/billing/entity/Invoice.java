package com.chennai.pos_backend.billing.entity;

import com.chennai.pos_backend.billing.enums.PaymentMethod;
import com.chennai.pos_backend.common.BaseEntity;
import com.chennai.pos_backend.shop.entity.Shop;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Invoice Entity
 *
 * Represents a customer bill generated for a specific shop.
 *
 * An Invoice contains:
 * - Shop information
 * - Invoice number
 * - Invoice date
 * - Subtotal
 * - CGST
 * - SGST
 * - Grand total
 * - Payment method
 * - Multiple invoice items
 *
 * Extends BaseEntity to inherit:
 * - id
 * - createdAt
 * - updatedAt
 */
@Entity
@Table(
        name = "invoices",

        // Ensures every invoice number is unique.
        uniqueConstraints = @UniqueConstraint(
                name = "uk_invoices_invoice_number",
                columnNames = "invoice_number"
        ),

        // Useful for shop-wise and date-wise invoice searches.
        indexes = @Index(
                name = "idx_invoices_shop_date",
                columnList = "shop_id, invoice_date"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity {

    /**
     * Shop to which this invoice belongs.
     *
     * Relationship:
     * Many Invoices -> One Shop
     *
     * Database:
     * invoices.shop_id -> shops.id
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    /**
     * Unique invoice number shown to the customer.
     *
     * Example:
     * INV-2026-000001
     */
    @Column(
            name = "invoice_number",
            nullable = false
    )
    private String invoiceNumber;

    /**
     * Date and time when the invoice was created.
     */
    @Column(
            name = "invoice_date",
            nullable = false
    )
    private LocalDateTime invoiceDate;

    /**
     * Total amount before adding GST.
     */
    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal subtotal;

    /**
     * Total CGST amount for the invoice.
     */
    @Column(
            name = "total_cgst",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal totalCgst;

    /**
     * Total SGST amount for the invoice.
     */
    @Column(
            name = "total_sgst",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal totalSgst;

    /**
     * Final amount payable by the customer.
     *
     * Usually:
     *
     * grandTotal =
     * subtotal + totalCgst + totalSgst
     */
    @Column(
            name = "grand_total",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal grandTotal;

    /**
     * Payment method used by the customer.
     *
     * Enum values are stored as strings.
     *
     * Example:
     * CASH
     * UPI
     * CARD
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_method",
            nullable = false,
            length = 20
    )
    private PaymentMethod paymentMethod;

    /**
     * Items included in this invoice.
     *
     * Relationship:
     * One Invoice -> Many InvoiceItems
     *
     * mappedBy = "invoice":
     * The InvoiceItem entity owns the relationship.
     *
     * cascade = ALL:
     * Invoice operations can cascade to its items.
     *
     * orphanRemoval = true:
     * Removes an InvoiceItem when it is
     * removed from the invoice collection.
     */
    @OneToMany(
            mappedBy = "invoice",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<InvoiceItem> items = new ArrayList<>();

    /**
     * Adds an item to this invoice.
     *
     * Keeps both sides of the bidirectional
     * relationship synchronized.
     */
    public void addItem(InvoiceItem item) {
        items.add(item);
        item.setInvoice(this);
    }
}