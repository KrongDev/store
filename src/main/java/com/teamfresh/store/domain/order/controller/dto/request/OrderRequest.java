package com.teamfresh.store.domain.order.controller.dto.request;

import com.teamfresh.store.domain.order.domain.aggregate.dto.OrderProductInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
        @NotEmpty String orderer,
        @NotEmpty String ordererAddress,
        @NotEmpty @Valid List<OrderProductInfo> orderProducts
) {
}
