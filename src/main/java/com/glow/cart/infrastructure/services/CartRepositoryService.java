package com.glow.cart.infrastructure.services;

import com.glow.cart.domain.model.Cart;
import com.glow.cart.domain.repository.CartRepository;
import com.glow.cart.infrastructure.mappers.CartEntityMapper;
import com.glow.cart.infrastructure.repository.CartJpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class CartRepositoryService implements CartRepository {
    private final CartJpaRepository jpaRepository;
    private final CartEntityMapper mapper;

    public CartRepositoryService(CartJpaRepository jpaRepository, CartEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void save(Cart cart) {
        jpaRepository.persist(mapper.toEntity(cart));
    }

    @Override
    @Transactional
    public void update(Cart cart) {
        jpaRepository.getEntityManager().merge(mapper.toEntity(cart));
    }

    @Override
    @Transactional
    public Optional<Cart> findById(String id) {
        return jpaRepository.findByIdOptional(id)
            .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public Optional<Cart> findByUserId(String userId) {
        return jpaRepository.findByUserId(userId)
            .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        return jpaRepository.deleteById(id);
    }
}
