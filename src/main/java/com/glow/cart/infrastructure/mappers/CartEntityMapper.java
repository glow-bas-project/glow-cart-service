package com.glow.cart.infrastructure.mappers;

import com.glow.cart.domain.model.Cart;
import com.glow.cart.infrastructure.repository.entities.CartJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CartEntityMapper {

    CartJpaEntity toEntity(Cart cart);
    Cart toDomain(CartJpaEntity entity);
}