package com.glow.cart.domain.model;

import com.glow.cart.domain.shared.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CartTest {

    @ParameterizedTest
    @NullAndEmptySource
    public void build_rejectsBlankUserId(String userId) {
        // given
        var builder = Cart.builder().userId(userId);

        // when
        var ex = assertThrows(DomainException.class, builder::build);

        // then
        assertEquals("User ID cannot be null or empty", ex.getMessage());
    }

    @Test
    public void build_createsCartWithValidUserId() {
        // given
        var userId = "user123";

        // when
        var cart = Cart.builder().userId(userId).build();

        // then
        assertNotNull(cart.getId());
        assertEquals(userId, cart.getUserId());
        assertNotNull(cart.getCreatedAt());
        assertNotNull(cart.getUpdatedAt());
        assertEquals(0, cart.getTotalPrice());
    }

    @Test
    public void build_initializesEmptyItems() {
        // given
        var userId = "user123";

        // when
        var cart = Cart.builder().userId(userId).build();

        // then
        assertNotNull(cart.getItems());
        assertEquals(0, cart.getItems().size());
    }

    @Test
    public void toBuilder_preservesCartState() {
        // given
        var originalCart = Cart.builder().userId("user123").totalPrice(5000).build();

        // when
        var newCart = originalCart.toBuilder().build();

        // then
        assertEquals(originalCart.getId(), newCart.getId());
        assertEquals(originalCart.getUserId(), newCart.getUserId());
        assertEquals(originalCart.getTotalPrice(), newCart.getTotalPrice());
    }
}
