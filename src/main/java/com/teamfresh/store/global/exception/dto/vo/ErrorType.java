package com.teamfresh.store.global.exception.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found."),
    INVALID_ORDER_PRODUCT(HttpStatus.BAD_REQUEST, "The order is invalid. Some of the requested products are invalid or there is an issue with the order information."),
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "Quantity cannot be null or zero. Please provide a valid quantity."),
    INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "Insufficient stock available. The requested quantity exceeds the available stock."),
    // Order
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "Order not found."),
    ;
    private final HttpStatus httpStatus;
    private final String reasonPhrase;
}