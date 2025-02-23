package com.teamfresh.store.global.exception;

import com.teamfresh.store.global.exception.dto.CustomGlobalException;
import com.teamfresh.store.global.exception.dto.vo.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //의도된 에러들을 캐치
    @ExceptionHandler(CustomGlobalException.class)
    public ResponseEntity<String> handleCustomException(CustomGlobalException ex) {
        ErrorType errorType = ex.getErrorType();
        log.warn(ex.getErrorType().getReasonPhrase(), ex);
        return ResponseEntity.status(errorType.getHttpStatus()).body(errorType.getReasonPhrase());
    }

    //진짜 에러들을 캐치
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        throw ex;
    }
}