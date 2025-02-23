package com.teamfresh.bookstore.domain.product.domain;

import com.teamfresh.bookstore.domain.product.domain.aggregate.Product;
import com.teamfresh.bookstore.domain.product.infra.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    @Transactional
    public void save(String name, Long amount) {
        repository.save(Product.of(name, amount));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void decrease(Long amount) {
        Product product = findById(amount);
        product.decreaseStock(amount);
    }

    @Transactional
    public void increase(Long amount) {
        Product product = findById(amount);
        product.increaseStock(amount);
    }
}
