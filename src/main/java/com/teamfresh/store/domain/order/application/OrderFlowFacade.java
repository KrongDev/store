package com.teamfresh.store.domain.order.application;

import com.teamfresh.store.domain.order.domain.OrderExcelService;
import com.teamfresh.store.domain.order.domain.OrderService;
import com.teamfresh.store.domain.order.domain.aggregate.OrderProduct;
import com.teamfresh.store.domain.order.domain.aggregate.dto.OrderProductInfo;
import com.teamfresh.store.domain.product.domain.ProductService;
import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import com.teamfresh.store.global.redis.annotation.RedisLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INVALID_ORDER_PRODUCT;

@Service
@RequiredArgsConstructor
public class OrderFlowFacade {
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderExcelService orderExcelService;

    /**
     * 상품 주문 처리
     * 1. product로부터 재고량 확인 및 점유 처리 ( 재고량을 줄여줌으로써 점유된 것으로 처리 )
     * 2. 주문처리
     */
    @RedisLock
    public void order(String orderer, String ordererAddress, List<OrderProductInfo> orderProducts) {
        Map<Long, Long> orderProductMap = orderProducts.stream().collect(Collectors.toMap(OrderProductInfo::productId, OrderProductInfo::quantity));
        productService.decreaseProducts(orderProductMap);
        List<OrderProduct> orderProductData = orderProducts.stream().map(OrderProductInfo::toDomain).toList();
        orderService.save(orderer, ordererAddress, orderProductData);
    }

    public void orderByExcel(String orderer, String ordererAddress, MultipartFile file) {
        List<OrderProductInfo> orderProductInfos = orderExcelService.excelParser(file);
        if(orderProductInfos.isEmpty()) {
            throw new CustomGlobalException(INVALID_ORDER_PRODUCT);
        }
        order(orderer, ordererAddress, orderProductInfos);
    }
}
