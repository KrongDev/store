package com.teamfresh.store.global.exception.dto;

import com.teamfresh.store.global.exception.dto.vo.ErrorType;
import lombok.Getter;

@Getter
public class CustomGlobalException extends RuntimeException {
    private final ErrorType errorType;

    public CustomGlobalException(ErrorType errorType) {
        super(errorType.getReasonPhrase());
        this.errorType = errorType;
    }
}