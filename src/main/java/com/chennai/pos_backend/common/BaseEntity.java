package com.chennai.pos_backend.common;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * BaseEntity is a common parent class that contains
 * fields needed by multiple database entities.
 *
 * @MappedSuperclass:
 * Shares persistent fields with child entities,
 * but does not create a standalone database table.
 */
@Getter
@Setter // Lombok automatically generates Getter and Setter methods.
@MappedSuperclass
public class BaseEntity {

    /**
     * Primary key of the entity.
     *
     * @Id: Identifies the primary key.
     * @GeneratedValue: Automatically generates the ID.
     * IDENTITY: Uses database auto-increment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Record creation timestamp.
     *
     * @Column:
     * Maps the Java field to the database column.
     *
     * updatable = false:
     * Prevents JPA from including this column
     * in SQL UPDATE statements.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Record last updated timestamp.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * JPA lifecycle callback.
     *
     * Executes before inserting a new record.
     *
     * Sets both creation and update timestamps.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * JPA lifecycle callback.
     *
     * Executes before updating an existing entity.
     *
     * Updates the updatedAt timestamp.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}