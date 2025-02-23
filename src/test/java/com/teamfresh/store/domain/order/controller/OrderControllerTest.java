package com.teamfresh.store.domain.order.controller;

import com.teamfresh.store.domain.order.application.OrderFlowFacade;
import com.teamfresh.store.domain.order.controller.dto.request.OrderRequest;
import com.teamfresh.store.domain.order.domain.aggregate.dto.OrderProductInfo;
import com.teamfresh.store.domain.product.domain.aggregate.Product;
import com.teamfresh.store.domain.product.infra.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("주문 동시성 테스트를 위한 통합 테스트")
class OrderControllerTest {
    @Autowired
    private OrderFlowFacade orderFlowFacade;
    @Autowired
    private ProductRepository productRepository;
    private List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Product product1 = productRepository.save(Product.of("test1", 10L, 100L));
        Product product2 = productRepository.save(Product.of("test2", 10L, 100L));
        products.add(product1);
        products.add(product2);
    }

    @Test
    @DisplayName("주문 생성 동시성 테스트 - 상품 재고량 부족으로 인해 주문 생성 10번중 1번 성공")
    void orderConcurrencyTest() throws InterruptedException {
        // GIVEN
        String orderer = "홍길동";
        String ordererAddress = "주소";
        List<OrderProductInfo> orderProductInfos = products.stream().map(product -> new OrderProductInfo(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice()
        )).toList();
        // WHEN
        int count = 10;
        ExecutorService executorsService = Executors.newFixedThreadPool(count);
        CountDownLatch doneLatch = new CountDownLatch(count);
        AtomicInteger successCounter = new AtomicInteger(0);
        for (int i = 0; i < count; i++) {
            executorsService.submit(() -> {
                try {
                    orderFlowFacade.order(orderer, ordererAddress, orderProductInfos);
                    successCounter.getAndAdd(1);
                } finally {
                    doneLatch.countDown(); // 작업 완료를 카운트
                }
            });
        }
        // 모든 작업이 완료되기를 대기
        doneLatch.await();
        // THEN
        assertEquals(1, successCounter.get());
    }
}
