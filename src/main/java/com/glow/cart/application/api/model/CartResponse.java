package com.glow.cart.application.api.model;

import com.glow.cart.application.model.CartItemDto;

import java.util.List;

public record CartResponse(
    String id,
    String userId,
    Integer totalPrice,
    List<CartItemDto> items
) {
}
