package com.chennai.pos_backend.eventlog.entity;

import com.chennai.pos_backend.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * shopId/userId are kept as plain FK ids rather than @ManyToOne relations —
 * event logging should stay decoupled and cheap to write, without pulling in
 * full Shop/User entities on every audited action. shopId is nullable for
 * platform-level Admin events (e.g. a new shop registering).
 *
 * Never log passwords, JWT tokens, or other sensitive credentials here.
 */
@Entity
@Table(
        name = "event_logs",
        indexes = @Index(
                name = "idx_event_logs_shop_created_type",
                columnList = "shop_id,created_at,event_type"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventLog extends BaseEntity {

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "user_id")
    private Long userId;

    // e.g. LOGIN_SUCCESS, LOGIN_FAILED, SHOP_REGISTERED, SHOP_DISABLED, SHOP_ENABLED,
    // OWNER_PASSWORD_RESET, CATEGORY_CREATED, CATEGORY_DISABLED, PRODUCT_CREATED,
    // PRODUCT_UPDATED, PRODUCT_DISABLED, STOCK_ADJUSTED, INVOICE_CREATED
    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Column(name = "entity_type", length = 50)
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    private String description;

    // MySQL JSON column; store as a serialized JSON string from the service layer.
    @Column(columnDefinition = "json")
    private String metadata;
}
