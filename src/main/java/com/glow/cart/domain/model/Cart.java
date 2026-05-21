package com.glow.cart.domain.model;

import com.glow.cart.domain.shared.DomainPrecondition;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Cart {
    private final UUID id;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final String userId;
    private final Integer totalPrice;
    private final List<CartItem> items;

    private Cart(Builder builder) {
        if (Objects.isNull(builder.id)) {
            this.id = UUID.randomUUID();
        } else {
            this.id = builder.id;
        }

        if (Objects.isNull(builder.createdAt)) {
            this.createdAt = Instant.now();
        } else {
            this.createdAt = builder.createdAt;
        }

        if (Objects.isNull(builder.updatedAt)) {
            this.updatedAt = Instant.now();
        } else {
            this.updatedAt = builder.updatedAt;
        }

        this.userId = DomainPrecondition.requireNonBlank(builder.userId,
            "User ID cannot be null or empty");

        if (Objects.isNull(builder.totalPrice)) {
            this.totalPrice = 0;
        } else {
            this.totalPrice = builder.totalPrice;
        }

        this.items = builder.items != null ? builder.items : List.of();
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Builder toBuilder() {
        return new Builder()
            .id(this.id)
            .createdAt(this.createdAt)
            .updatedAt(this.updatedAt)
            .userId(this.userId)
            .totalPrice(this.totalPrice)
            .items(this.items);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(createdAt, cart.createdAt) &&
            Objects.equals(updatedAt, cart.updatedAt) && Objects.equals(userId, cart.userId) &&
            Objects.equals(totalPrice, cart.totalPrice) && Objects.equals(items, cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, userId, totalPrice, items);
    }

    public static class Builder {
        private UUID id;
        private Instant createdAt;
        private Instant updatedAt;
        private String userId;
        private Integer totalPrice;
        private List<CartItem> items;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder totalPrice(Integer totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder items(List<CartItem> items) {
            this.items = items;
            return this;
        }

        public Cart build() {
            return new Cart(this);
        }
    }
}
