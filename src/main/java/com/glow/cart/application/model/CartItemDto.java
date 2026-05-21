package com.glow.cart.application.model;

import java.util.List;

public record CartItemDto(
    String id,
    String cartId,
    String menuItemId,
    Integer quantity,
    Integer price
) {}
