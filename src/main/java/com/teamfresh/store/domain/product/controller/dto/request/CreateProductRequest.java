package com.teamfresh.store.domain.product.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateProductRequest(
        @NotEmpty String name,
        Long quantity,
        Long price
) {
        public CreateProductRequest {
                if (quantity == null) quantity = 0L;
                if (price == null) price = 0L;
        }
}
