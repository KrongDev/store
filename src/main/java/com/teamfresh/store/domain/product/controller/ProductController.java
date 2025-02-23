package com.teamfresh.store.domain.product.controller;

import com.teamfresh.store.domain.product.controller.dto.request.CreateProductRequest;
import com.teamfresh.store.domain.product.controller.dto.request.IncreaseRequest;
import com.teamfresh.store.domain.product.domain.ProductService;
import com.teamfresh.store.domain.product.domain.aggregate.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public void createProduct(@Valid @RequestBody CreateProductRequest request) {
        productService.save(request.name(), request.quantity(), request.price());
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @PatchMapping("/increase")
    public void increase(@Valid @RequestBody IncreaseRequest request) {
        productService.increase(request.productId(), request.quantity());
    }
}
