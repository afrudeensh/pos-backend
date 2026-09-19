package com.chennai.pos_backend.common.exception;

import com.chennai.pos_backend.common.response.BaseResponse;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<BaseResponse<Object>> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(BaseResponse.error(ex.getStatus().value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), message));
    }

    // Thrown when optimistic locking (@Version) detects a concurrent update conflict.
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<BaseResponse<Object>> handleOptimisticLock(OptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BaseResponse.error(HttpStatus.CONFLICT.value(),
                        "The record was updated concurrently. Please retry."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(BaseResponse.error(HttpStatus.FORBIDDEN.value(), "Access denied"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong. Please try again later."));
    }
}
