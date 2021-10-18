package com.github.gustavodslara.compasso.productms.adapters.outbound.persistence.repository;

import com.github.gustavodslara.compasso.productms.adapters.outbound.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    default List<ProductEntity> getProductsByQuery(EntityManager entityManager, String q, BigDecimal min, BigDecimal max) {
        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(ProductEntity.class);
        var root = cq.from(ProductEntity.class);

        Predicate predicateName = null;
        Predicate predicateDescription = null;
        if (q != null) {
            var formattedQ = MessageFormat.format("%{0}%", q);
            predicateName = cb.like(cb.lower(root.get("name")), formattedQ); //CriteriaQuery.like não está usando %%, não funcionando corretamente
            predicateDescription = cb.like(root.get("description"), formattedQ);
        }

        cq.where(cb.or(predicateName, predicateDescription));

        var predicateMinPrice = min != null ? cb.greaterThan(root.get("price"), min) : null;
        var predicateMaxPrice = max != null ? cb.lessThan(root.get("price"), max) : null;

        if (predicateMinPrice != null && predicateMaxPrice != null) {
            cq.where(cb.and(predicateMinPrice, predicateMaxPrice));
        } else if (predicateMinPrice != null) {
            cq.where(predicateMinPrice);
        } else if (predicateMaxPrice != null) {
            cq.where(predicateMaxPrice);
        }


        return entityManager.createQuery(cq).getResultList();

    }
}
