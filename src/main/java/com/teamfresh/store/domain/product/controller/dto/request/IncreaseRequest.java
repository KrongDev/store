package com.teamfresh.store.domain.product.controller.dto.request;

import jakarta.validation.constraints.Positive;

public record IncreaseRequest (
        @Positive Long productId,
        @Positive Long quantity
) {
}
