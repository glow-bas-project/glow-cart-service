package com.glow.cart.infrastructure.repository;

import com.glow.cart.domain.shared.PageRequest;
import com.glow.cart.domain.shared.PageResult;
import com.glow.cart.infrastructure.repository.entities.CartJpaEntity;
import com.glow.cart.infrastructure.repository.projections.CartIdProjection;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CartJpaRepository implements PanacheRepositoryBase<CartJpaEntity, String> {

    public PageResult<String> findIds(PageRequest pageRequest) {
        var query = findAll()
            .page(pageRequest.page(), pageRequest.size())
            .project(CartIdProjection.class);
        var hasNext = query.hasNextPage();

        return new PageResult<>(
            query.list().stream().map(CartIdProjection::id).toList(),
            hasNext ? pageRequest.page() + 1 : pageRequest.page(),
            !hasNext);
    }

    public List<CartJpaEntity> findCartsByIds(List<String> ids) {
        return list("id in ?1", ids);
    }

    public Optional<CartJpaEntity> findByUserId(String userId) {
        return find("user_id", userId).firstResultOptional();
    }
}
