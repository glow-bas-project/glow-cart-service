package com.glow.cart.application.api.model;

public record AddCartItemRequest(
    String menuItemId,
    String name,
    Integer quantity,
    Integer price
) {
}
