package com.teamfresh.store.domain.order.domain.aggregate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;

@DisplayName("주문 테스트")
class OrderTest {
    private Order order;
    private OrderProduct orderProduct1;
    private OrderProduct orderProduct2;

    @BeforeEach
    void setUp() {
        // GIVEN
        orderProduct1 = OrderProduct.of(1L, "상품1", 2L, 10000L);
        orderProduct2 = OrderProduct.of(2L, "상품2", 1L, 15000L);
        order = Order.of("홍길동", "서울시 강남구", List.of(orderProduct1, orderProduct2));
    }

    @Test
    @DisplayName("주문 생성 테스트")
    void orderTest() {
        //THEN
        assertThat(order).isNotNull();
        assertThat(order.getOrderer()).isEqualTo("홍길동");
        assertThat(order.getOrdererAddress()).isEqualTo("서울시 강남구");
        assertThat(order.getOrderDate()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(order.getTotalPrice()).isEqualTo(35000L);
        assertThat(order.getOrderProducts()).hasSize(2);
    }

    @Test
    @DisplayName("주문 상품 추가 테스트")
    void addOrderProduct() {
        //WHEN
        OrderProduct newOrderProduct = OrderProduct.of(3L, "상품3", 3L, 5000L);
        order.addOrderProduct(newOrderProduct);
        //THEN
        assertThat(order.getOrderProducts()).hasSize(3);
        assertThat(order.getTotalPrice()).isEqualTo(50000L);
    }
}