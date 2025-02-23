package com.teamfresh.store.domain.order.domain.aggregate.dto;


import com.teamfresh.store.domain.order.domain.aggregate.OrderProduct;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record OrderProductInfo(
        @Positive Long productId,
        @NotEmpty String productName,
        @Positive Long quantity,
        Long price
) {
    public OrderProductInfo {
        if(price==null) {
            price = 0L;
        }
    }

    public static OrderProductInfo from(OrderProduct orderProduct) {
        return new OrderProductInfo(
                orderProduct.getProductId(),
                orderProduct.getProductName(),
                orderProduct.getQuantity(),
                orderProduct.getPrice()
        );
    }

    public OrderProduct toDomain() {
        return OrderProduct.of(productId, productName, quantity, price);
    }
}
