package com.teamfresh.store.domain.product.domain;

import com.teamfresh.store.domain.product.domain.aggregate.Product;
import com.teamfresh.store.domain.product.infra.ProductRepository;
import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INVALID_ORDER_PRODUCT;
import static com.teamfresh.store.global.exception.dto.vo.ErrorType.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    @Transactional
    public void save(String name, Long quantity, Long price) {
        repository.save(Product.of(name, quantity, price));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllByIds(Collection<Long> ids) {
        return repository.findAllByIdInForLock(ids);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomGlobalException(PRODUCT_NOT_FOUND));
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Product product = findById(id);
        product.decreaseStock(quantity);
    }

    @Transactional
    public void increase(Long id, Long quantity) {
        Product product = findById(id);
        product.increaseStock(quantity);
    }

    // 분산락 적용
    @Transactional
    public void decreaseProducts(Map<Long, Long> orderProducts) {
        List<Product> products = findAllByIds(orderProducts.keySet());
        if(products.size() != orderProducts.size()) {
            throw new CustomGlobalException(INVALID_ORDER_PRODUCT);
        }
        products.forEach(product -> product.decreaseStock(orderProducts.get(product.getId())));
    }
}
