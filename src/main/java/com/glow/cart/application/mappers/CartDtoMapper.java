package com.glow.cart.application.mappers;

import com.glow.cart.application.model.CartDto;
import com.glow.cart.domain.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CartDtoMapper {

    CartDto toDto(Cart cart);
    Cart toDomain(CartDto dto);
}
