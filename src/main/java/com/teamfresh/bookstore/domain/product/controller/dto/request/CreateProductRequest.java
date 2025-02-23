package com.teamfresh.bookstore.domain.product.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateProductRequest(
        @NotEmpty
        String name,
        Long amount
) {
}
