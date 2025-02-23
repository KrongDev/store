package com.teamfresh.store.domain.order.controller.dto.response;

import com.teamfresh.store.domain.order.domain.aggregate.Order;
import com.teamfresh.store.domain.order.domain.aggregate.dto.OrderProductInfo;

import java.util.List;

public record OrderResponse (
        Long id,
        String orderer,
        String ordererAddress,
        Long totalPrice,
        List<OrderProductInfo> orderProducts
){

    public static OrderResponse from(Order order){
        return new OrderResponse(
                order.getId(),
                order.getOrderer(),
                order.getOrdererAddress(),
                order.getTotalPrice(),
                order.getOrderProducts().stream().map(OrderProductInfo::from).toList()
        );
    }
}
