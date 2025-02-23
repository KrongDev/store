package com.teamfresh.store.domain.product.infra;

import com.teamfresh.store.domain.product.domain.aggregate.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id in (:ids) ")
    List<Product> findAllByIdInForLock(Collection<Long> ids);

    Optional<Product> findById(Long id);
}
