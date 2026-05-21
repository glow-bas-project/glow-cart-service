package com.glow.cart.application.model;

import java.util.List;

public record CartDto(
    String id,
    String userId,
    Integer totalPrice,
    List<CartItemDto> items
) {
}
