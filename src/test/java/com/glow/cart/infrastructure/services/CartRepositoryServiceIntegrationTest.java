package com.glow.cart.infrastructure.services;

import com.glow.cart.domain.model.Cart;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class CartRepositoryServiceIntegrationTest {

    @Inject
    CartRepositoryService repository;

    @Test
    @Transactional
    public void saveAndFindById_roundTrips() {
        // given
        var cart = newCart("user-integration-1");

        // when
        repository.save(cart);
        var id = cart.getId().toString();
        var found = repository.findById(id);

        // then
        assertTrue(found.isPresent());
        assertEquals("user-integration-1", found.get().getUserId());
        assertEquals(0, found.get().getTotalPrice());
    }

    @Test
    @Transactional
    public void findByUserId_returnsCartForUser() {
        // given
        var userId = "user-integration-2";
        var cart = newCart(userId);
        repository.save(cart);

        // when
        var found = repository.findByUserId(userId);

        // then
        assertTrue(found.isPresent());
        assertEquals(userId, found.get().getUserId());
    }

    @Test
    @Transactional
    public void updateCart_modifiesTotalPrice() {
        // given
        var cart = newCart("user-integration-3");
        repository.save(cart);
        var id = cart.getId().toString();
        var updatedCart = cart.toBuilder().totalPrice(7500).build();

        // when
        repository.update(updatedCart);
        var found = repository.findById(id);

        // then
        assertTrue(found.isPresent());
        assertEquals(7500, found.get().getTotalPrice());
    }

    @Test
    @Transactional
    public void deleteById_removesCart() {
        // given
        var cart = newCart("user-integration-4");
        repository.save(cart);
        var id = cart.getId().toString();

        // when
        var deleted = repository.deleteById(id);
        var after = repository.findById(id);

        // then
        assertTrue(deleted);
        assertTrue(after.isEmpty());
    }

    private static Cart newCart(String userId) {
        return Cart.builder()
            .userId(userId)
            .totalPrice(0)
            .build();
    }
}
