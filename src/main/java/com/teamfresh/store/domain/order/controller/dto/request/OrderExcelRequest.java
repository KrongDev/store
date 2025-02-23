package com.teamfresh.store.domain.order.controller.dto.request;

import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import org.springframework.web.multipart.MultipartFile;

import static com.teamfresh.store.global.exception.dto.vo.ErrorType.INVALID_ORDER_PRODUCT;

public record OrderExcelRequest(
        String orderer,
        String ordererAddress,
        MultipartFile excel
) {
    public void validate() {
        if(orderer == null || orderer.isEmpty() ||  ordererAddress == null || ordererAddress.isEmpty()) {
            throw new CustomGlobalException(INVALID_ORDER_PRODUCT);
        }
    }
}
