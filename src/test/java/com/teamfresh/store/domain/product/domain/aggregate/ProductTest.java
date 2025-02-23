package com.teamfresh.store.domain.product.domain.aggregate;

import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INSUFFICIENT_STOCK;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INVALID_QUANTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("상품 테스트")
class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        // GIVEN
        product = Product.of("테스트상품", 10L, 2000L);
    }

    @Test
    @DisplayName("상품 재고 감소 성공 테스트")
    void productDecreaseStockTest() {
        //WHEN
        product.decreaseStock(5L);
        //THEN
        assertThat(product.getQuantity()).isEqualTo(5L);
    }

    @Test
    @DisplayName("상품 재고 감소 실패 테스트 - 잘못된 수량")
    void productDecreaseStockTestInvalidQuantity() {
        // WHEN-THEN
        assertThatThrownBy(() -> product.decreaseStock(0L))
                .isInstanceOf(CustomGlobalException.class)
                .hasMessage(INVALID_QUANTITY.getReasonPhrase());
    }

    @Test
    @DisplayName("상품 재고 감소 실패 테스트 - 재고부족")
    void productDecreaseStockTestInsufficientStock() {
        // WHEN-THEN
        assertThatThrownBy(() -> product.decreaseStock(11L))
                .isInstanceOf(CustomGlobalException.class)
                .hasMessage(INSUFFICIENT_STOCK.getReasonPhrase());
    }

    @Test
    @DisplayName("상품 재고 증가 테스트")
    void productIncreaseStockTest() {
        // WHEN-THEN
        product.increaseStock(5L);
        assertThat(product.getQuantity()).isEqualTo(15L);
    }
}