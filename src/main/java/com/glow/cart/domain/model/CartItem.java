package com.glow.cart.domain.model;

public record CartItem(
    String id,
    String cartId,
    String menuItemId,
    String name,
    Integer quantity,
    Integer price
) {
}
