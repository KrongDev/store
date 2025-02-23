package com.teamfresh.store.domain.product.controller.dto.request;

import jakarta.validation.constraints.Positive;

public record DecreaseRequest(
        @Positive Long quantity
) {
}
