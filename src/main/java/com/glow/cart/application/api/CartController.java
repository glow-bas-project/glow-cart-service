package com.glow.cart.application.api;

import com.glow.cart.application.api.model.*;
import com.glow.cart.application.model.CartDto;
import com.glow.cart.application.services.CartService;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartController {

    private final CartService service;
    private final JsonWebToken jwt;

    public CartController(CartService service, JsonWebToken jwt) {
        this.service = service;
        this.jwt = jwt;
    }

    // ========== CREATE CART ==========

    @POST
    public RestResponse<CartResponse> createCart(CreateCartRequest request) {
        CartDto cartDto = service.createCart(request.userId());
        return RestResponse.status(RestResponse.Status.CREATED, mapToResponse(cartDto));
    }

    // ========== GET CART ==========

    @GET
    @Path("/{id}")
    public RestResponse<CartResponse> getCartById(@PathParam("id") String id) {
        CartDto cartDto = service.findById(id);
        return RestResponse.ok(mapToResponse(cartDto));
    }

    // ========== GET OR CREATE MY CART ==========

    @GET
    @Path("/me")
    @Authenticated
    public RestResponse<CartResponse> getOrCreateMyCart() {
        CartDto cartDto = service.getOrCreateCart(jwt.getSubject());
        return RestResponse.ok(mapToResponse(cartDto));
    }

    @GET
    @Path("/user/{userId}")
    public RestResponse<CartResponse> getCartByUserId(@PathParam("userId") String userId) {
        CartDto cartDto = service.findByUserId(userId);
        return RestResponse.ok(mapToResponse(cartDto));
    }

    // ========== UPDATE CART ==========

    @PUT
    @Path("/{id}")
    public RestResponse<CartResponse> updateCart(@PathParam("id") String id, UpdateCartRequest request) {
        CartDto existingCart = service.findById(id);
        CartDto updatedCart = new CartDto(
            existingCart.id(),
            existingCart.userId(),
            request.totalPrice(),
            existingCart.items()
        );
        CartDto cartDto = service.updateCart(updatedCart);
        return RestResponse.ok(mapToResponse(cartDto));
    }

    // ========== CLEAR CART ==========

    @POST
    @Path("/{id}/clear")
    public RestResponse<CartResponse> clearCart(@PathParam("id") String id) {
        CartDto existingCart = service.findById(id);
        CartDto clearedCart = new CartDto(
            existingCart.id(),
            existingCart.userId(),
            0,
            java.util.List.of()
        );
        CartDto cartDto = service.updateCart(clearedCart);
        return RestResponse.ok(mapToResponse(cartDto));
    }

    @DELETE
    @Path("/me")
    @Authenticated
    public RestResponse<Void> clearMyCart() {
        service.clearCart(jwt.getSubject());
        return RestResponse.noContent();
    }

    // ========== Helper method ==========

    private CartResponse mapToResponse(CartDto cartDto) {
        return new CartResponse(
            cartDto.id(),
            cartDto.userId(),
            cartDto.totalPrice(),
            cartDto.items()
        );
    }
}
