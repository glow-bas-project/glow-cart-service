package com.glow.cart.application.services;

import com.glow.cart.application.mappers.CartDtoMapper;
import com.glow.cart.application.model.CartDto;
import com.glow.cart.domain.model.Cart;
import com.glow.cart.domain.repository.CartRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import com.glow.cart.application.model.CartItemDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @Mock
    CartDtoMapper mapper;

    @InjectMocks
    CartService service;

    @Test
    public void createCart_savesAndMapsToDto() {
        // given
        var userId = "user123";
        when(mapper.toDto(any(Cart.class))).thenAnswer(invocation -> {
            Cart c = invocation.getArgument(0);
            var item = new CartItemDto("item-1", c.getId().toString(), "menu-1", "menu-item-2-name", 1, 100);
            return new CartDto(
                c.getId().toString(),
                c.getUserId(),
                c.getTotalPrice(),
                List.of(item));
        });

        // when
        var dto = service.createCart(userId);

        // then
        assertEquals(userId, dto.userId());
        assertEquals(0, dto.totalPrice());
        verify(cartRepository).save(any(Cart.class));
        verify(mapper).toDto(any(Cart.class));
    }

    @Test
    public void findById_throwsWhenMissing() {
        // given
        when(cartRepository.findById("missing")).thenReturn(Optional.empty());

        // when / then
        assertThrows(NotFoundException.class, () -> service.findById("missing"));
    }

    @Test
    public void findById_returnsDtoWhenPresent() {
        // given
        var cart = Cart.builder()
            .userId("user123")
            .totalPrice(1000)
            .build();
        var expected = new CartDto(
            cart.getId().toString(),
            cart.getUserId(),
            cart.getTotalPrice(),
            List.of(new CartItemDto("item-1", cart.getId().toString(), "menu-1", "menu-item-2-name", 1, 100)));
        when(cartRepository.findById(expected.id())).thenReturn(Optional.of(cart));
        when(mapper.toDto(cart)).thenReturn(expected);

        // when
        var actual = service.findById(expected.id());

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void findByUserId_throwsWhenMissing() {
        // given
        when(cartRepository.findByUserId("missing-user")).thenReturn(Optional.empty());

        // when / then
        assertThrows(NotFoundException.class, () -> service.findByUserId("missing-user"));
    }

    @Test
    public void findByUserId_returnsDtoWhenPresent() {
        // given
        var userId = "user123";
        var cart = Cart.builder()
            .userId(userId)
            .totalPrice(2500)
            .build();
        var expected = new CartDto(
            cart.getId().toString(),
            cart.getUserId(),
            cart.getTotalPrice(),
            List.of(new CartItemDto("item-1", cart.getId().toString(), "menu-1", "menu-item-2-name", 1, 100)));
        when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));
        when(mapper.toDto(cart)).thenReturn(expected);

        // when
        var actual = service.findByUserId(userId);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void updateCart_savesAndReturnsMappedDto() {
        // given
        var cartDto = new CartDto("id123", "user123", 5000, List.of());
        var updatedCart = Cart.builder()
            .userId("user123")
            .totalPrice(5000)
            .build();
        when(mapper.toDomain(cartDto)).thenReturn(updatedCart);
        when(mapper.toDto(updatedCart)).thenReturn(cartDto);

        // when
        var result = service.updateCart(cartDto);

        // then
        assertEquals(5000, result.totalPrice());
        verify(cartRepository).update(any(Cart.class));
    }

    @Test
    public void deleteCart_returnsTrueWhenRepositoryDeletes() {
        // given
        when(cartRepository.deleteById("id123")).thenReturn(true);

        // when
        var deleted = service.deleteCart("id123");

        // then
        assertTrue(deleted);
        verify(cartRepository).deleteById("id123");
    }
}
