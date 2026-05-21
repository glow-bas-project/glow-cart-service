package com.glow.cart.application.api.model;

import java.time.Instant;

public record ApiError(
    String type,
    String message,
    int statusCode,
    Instant timestamp
) {
}
