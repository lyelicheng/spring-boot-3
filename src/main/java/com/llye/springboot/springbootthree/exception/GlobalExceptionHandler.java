package com.llye.springboot.springbootthree.exception;

import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus().value(), ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @Value
    private static class ErrorResponse {
        int status;
        String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
