package com.glow.cart.application.services;

import java.time.Instant;
import java.util.List;

import com.glow.cart.application.mappers.CartDtoMapper;
import com.glow.cart.application.model.CartDto;
import com.glow.cart.domain.model.Cart;
import com.glow.cart.domain.repository.CartRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CartService {
    private final CartRepository cartRepository;
    private final CartDtoMapper mapper;

    public CartService(CartRepository cartRepository, CartDtoMapper mapper) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }

    public CartDto createCart(String userId) {
        var cart = Cart.builder()
            .userId(userId)
            .build();

        cartRepository.save(cart);
        return mapper.toDto(cart);
    }

    public CartDto getOrCreateCart(String keycloakSubject) {
        return cartRepository.findByUserId(keycloakSubject)
            .map(mapper::toDto)
            .orElseGet(() -> createCart(keycloakSubject));
    }

    public void clearCart(String keycloakSubject) {
        cartRepository.findByUserId(keycloakSubject).ifPresent(cart -> {
            var cleared = cart.toBuilder()
                .totalPrice(0)
                .items(List.of())
                .updatedAt(Instant.now())
                .build();
            cartRepository.update(cleared);
        });
    }

    public CartDto findById(String id) {
        return mapper.toDto(
            cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart with id " + id + " not found")));
    }

    public CartDto findByUserId(String userId) {
        return mapper.toDto(
            cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Cart for user " + userId + " not found")));
    }

    public CartDto updateCart(CartDto cartDto) {
        var cart = mapper.toDomain(cartDto);
        cartRepository.update(cart);
        return mapper.toDto(cart);
    }

    public boolean deleteCart(String id) {
        return cartRepository.deleteById(id);
    }
}
