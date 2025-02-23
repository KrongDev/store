package com.teamfresh.store.domain.order.domain;

import com.teamfresh.store.domain.order.domain.aggregate.Order;
import com.teamfresh.store.domain.order.domain.aggregate.OrderProduct;
import com.teamfresh.store.domain.order.infra.OrderRepository;
import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.ORDER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void save(String orderer, String ordererAddress, List<OrderProduct> orderProducts) {
        orderRepository.save(Order.of(orderer, ordererAddress, orderProducts));
    }

    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomGlobalException(ORDER_NOT_FOUND));
    }
}
