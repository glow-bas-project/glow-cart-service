package com.glow.cart.infrastructure.mappers;

import com.glow.cart.domain.model.Cart;
import com.glow.cart.domain.model.CartItem;
import com.glow.cart.infrastructure.repository.entities.CartJpaEntity;
import com.glow.cart.infrastructure.repository.entities.CartItemJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "cdi")
public interface CartEntityMapper {

    default CartJpaEntity toEntity(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartJpaEntity entity = new CartJpaEntity();
        entity.setId(cart.getId().toString());
        entity.setCreatedAt(cart.getCreatedAt());
        entity.setUpdatedAt(cart.getUpdatedAt());
        entity.setUserId(cart.getUserId());
        entity.setTotalPrice(cart.getTotalPrice());
        
        List<CartItemJpaEntity> items = cart.getItems().stream()
            .map(item -> new CartItemJpaEntity(
                item.menuItemId(),
                item.quantity(),
                item.price()
            ))
            .toList();
        entity.setItems(items);
        return entity;
    }

    default Cart toDomain(CartJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        List<CartItem> items = entity.getItems().stream()
            .map(jpaItem -> new CartItem(
                UUID.randomUUID().toString(),
                entity.getId(),
                jpaItem.getMenuItemId(),
                jpaItem.getQuantity(),
                jpaItem.getPrice()
            ))
            .toList();

        return Cart.builder()
            .id(entity.getId() != null ? java.util.UUID.fromString(entity.getId()) : null)
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .userId(entity.getUserId())
            .totalPrice(entity.getTotalPrice())
            .items(items)
            .build();
    }
}
