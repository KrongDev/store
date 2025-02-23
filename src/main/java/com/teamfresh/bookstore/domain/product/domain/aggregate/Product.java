package com.teamfresh.bookstore.domain.product.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "product")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "상품 명")
    private String name;
    @Column(nullable = false, columnDefinition = "재고")
    private Long stock;

    public Product(String name, Long amount) {
        this.name = name;
        this.stock = amount;
    }

    public static Product of(String name, Long amount) {
        return new Product(name, amount);
    }

    public void decreaseStock(Long amount) {
        if(stock - amount < 0) {
            throw new IllegalArgumentException("amount can not be less than zero");
        }
        this.stock -= amount;
    }

    public void increaseStock(Long amount) {
        this.stock += amount;
    }
}
