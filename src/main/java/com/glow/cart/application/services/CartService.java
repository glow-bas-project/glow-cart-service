package com.glow.cart.application.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.glow.cart.application.api.model.AddCartItemRequest;
import com.glow.cart.application.mappers.CartDtoMapper;
import com.glow.cart.application.model.CartDto;
import com.glow.cart.domain.model.Cart;
import com.glow.cart.domain.model.CartItem;
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

    public CartDto addItemToCart(String keycloakSubject, AddCartItemRequest request) {
        CartDto cartDto = getOrCreateCart(keycloakSubject);
        Cart cart = mapper.toDomain(cartDto);

        List<CartItem> updatedItems = new ArrayList<>(cart.getItems());
        boolean itemExists = false;

        for (int i = 0; i < updatedItems.size(); i++) {
            CartItem existingItem = updatedItems.get(i);
            if (existingItem.menuItemId().equals(request.menuItemId())) {
                updatedItems.set(i, new CartItem(
                    existingItem.id(),
                    existingItem.cartId(),
                    existingItem.menuItemId(),
                    existingItem.name() != null ? existingItem.name() : request.name(),
                    existingItem.quantity() + request.quantity(),
                    existingItem.price()
                ));
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            CartItem newItem = new CartItem(
                UUID.randomUUID().toString(),
                cart.getId().toString(),
                request.menuItemId(),
                request.name(),
                request.quantity(),
                request.price()
            );
            updatedItems.add(newItem);
        }

        Integer newTotalPrice = cart.getTotalPrice() + (request.price() * request.quantity());

        Cart updatedCart = cart.toBuilder()
            .items(updatedItems)
            .totalPrice(newTotalPrice)
            .updatedAt(Instant.now())
            .build();

        cartRepository.update(updatedCart);
        return mapper.toDto(updatedCart);
    }
}
