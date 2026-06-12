package com.glow.cart.application.model;

import java.util.List;

public record CartItemDto(
    String id,
    String cartId,
    String menuItemId,
    String name,
    Integer quantity,
    Integer price
) {}
