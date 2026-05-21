package com.glow.cart.infrastructure.repository.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartJpaEntity {

    @Id
    private String id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_price")
    private Integer totalPrice;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    private List<CartItemJpaEntity> items = new ArrayList<>();

    public CartJpaEntity() {
    }

    public CartJpaEntity(String id, Instant createdAt, Instant updatedAt, String userId, Integer totalPrice, List<CartItemJpaEntity> items) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemJpaEntity> getItems() {
        return items;
    }

    public void setItems(List<CartItemJpaEntity> items) {
        this.items = items;
    }
}
