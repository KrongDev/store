package com.teamfresh.store.domain.order.controller;

import com.teamfresh.store.domain.order.application.OrderFlowFacade;
import com.teamfresh.store.domain.order.controller.dto.request.OrderRequest;
import com.teamfresh.store.domain.order.controller.dto.response.OrderResponse;
import com.teamfresh.store.domain.order.domain.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderFlowFacade orderFlowFacade;

    @PostMapping()
    public void order(@Valid @RequestBody OrderRequest request) {
        //
        orderFlowFacade.order(request.orderer(), request.ordererAddress(), request.orderProducts());
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return OrderResponse.from(orderService.findById(orderId));
    }
}
