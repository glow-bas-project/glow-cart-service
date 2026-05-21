package com.glow.cart.application.mappers;

import com.glow.cart.application.model.CartItemDto;
import com.glow.cart.domain.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CartItemDtoMapper {

    CartItemDto toDto(CartItem cartItem);
    CartItem toDomain(CartItemDto dto);
}
