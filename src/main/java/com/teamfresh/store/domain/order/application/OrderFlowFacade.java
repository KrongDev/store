package com.teamfresh.store.domain.order.application;

import com.teamfresh.store.domain.order.domain.OrderService;
import com.teamfresh.store.domain.order.domain.aggregate.OrderProduct;
import com.teamfresh.store.domain.order.domain.aggregate.dto.OrderProductInfo;
import com.teamfresh.store.domain.product.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFlowFacade {
    private final ProductService productService;
    private final OrderService orderService;

    /**
     * 상품 주문 처리
     * 1. product로부터 재고량 확인 및 점유 처리 ( 재고량을 줄여줌으로써 점유된 것으로 처리 )
     * 2. 주문처리
     */
    public void order(String orderer, String ordererAddress, List<OrderProductInfo> orderProducts) {
        Map<Long, Long> orderProductMap = orderProducts.stream().collect(Collectors.toMap(OrderProductInfo::productId, OrderProductInfo::quantity));
        productService.decreaseProducts(orderProductMap);
        List<OrderProduct> orderProductData = orderProducts.stream().map(OrderProductInfo::toDomain).toList();
        orderService.save(orderer, ordererAddress, orderProductData);
    }
}
