package com.glow.cart.infrastructure.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.Instant;

@Embeddable
public class CartItemJpaEntity {
    @Column(name = "menu_item_id")
    private String menuItemId;

    @Column(name = "item_quantity")
    private Integer quantity;

    @Column(name = "item_price")
    private Integer price;

    public CartItemJpaEntity() {
    }

    public CartItemJpaEntity(String menuItemId, Integer quantity, Integer price) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
