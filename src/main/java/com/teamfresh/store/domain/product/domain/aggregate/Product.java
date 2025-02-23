package com.teamfresh.store.domain.product.domain.aggregate;

import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import jakarta.persistence.*;
import lombok.*;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INSUFFICIENT_STOCK;
import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INVALID_QUANTITY;

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
    @Column(nullable = false, name = "상품 명")
    private String name;
    @Column(nullable = false, name = "재고")
    private Long quantity;
    @Column(nullable = false, name = "개당 가격")
    private Long price;

    public static Product of(String name, Long quantity, Long price) {
        return Product.builder()
                .name(name)
                .quantity(quantity)
                .price(price)
                .build();
    }

    public void decreaseStock(Long quantity) {
        if(quantity == null || quantity <= 0) {
            throw new CustomGlobalException(INVALID_QUANTITY);
        }
        if(this.quantity - quantity < 0) {
            throw new CustomGlobalException(INSUFFICIENT_STOCK);
        }
        this.quantity -= quantity;
    }

    public void increaseStock(Long quantity) {
        if(quantity == null || quantity <= 0) {
            throw new CustomGlobalException(INVALID_QUANTITY);
        }
        this.quantity += quantity;
    }

}
