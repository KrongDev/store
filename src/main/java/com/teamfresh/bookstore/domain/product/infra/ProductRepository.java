package com.teamfresh.bookstore.domain.product.infra;

import com.teamfresh.bookstore.domain.product.domain.aggregate.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdDesc();
    Optional<Product> findById(Long id);
}
