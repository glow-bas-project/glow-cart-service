package com.glow.cart.application.api.model;

public record AddCartItemRequest(
    String menuItemId,
    Integer quantity,
    Integer price
) {
}
