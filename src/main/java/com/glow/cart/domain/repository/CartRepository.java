package com.glow.cart.domain.repository;

import com.glow.cart.domain.model.Cart;

import java.util.Optional;

public interface CartRepository {
    void save(Cart cart);

    void update(Cart cart);

    Optional<Cart> findById(String id);

    Optional<Cart> findByUserId(String userId);

    boolean deleteById(String id);
}
