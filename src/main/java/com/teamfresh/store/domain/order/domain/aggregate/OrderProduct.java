package com.teamfresh.store.domain.order.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "order_product")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false, name = "상품 PK")
    private Long productId;
    @Column(nullable = false, name = "상품명")
    private String productName;
    @Column(nullable = false, name = "상품 갯수")
    private Long quantity;
    @Column(name = "상품 개별 금액")
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public static OrderProduct of(Long productId, String productName, Long quantity, Long price) {
        return OrderProduct.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .price(price)
                .build();
    }

    protected void setOrder(Order order) {
        this.order = order;
    }
}
