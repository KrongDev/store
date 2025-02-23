package com.teamfresh.bookstore.domain.product.controller.dto.request;

import jakarta.validation.constraints.Positive;

public record IncreaseRequest (
        @Positive Long amount
) {
}
