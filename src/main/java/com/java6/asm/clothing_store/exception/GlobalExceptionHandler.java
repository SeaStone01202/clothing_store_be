package com.java6.asm.clothing_store.exception;

import com.java6.asm.clothing_store.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException() {
        ApiResponse apiResponse = ApiResponse.builder()
                        .status(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = ApiResponse.builder().
                status(errorCode.getCode()).
                message(errorCode.getMessage()).build();

        return ResponseEntity.
                status(errorCode.getHttpStatus()).
                body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ApiResponse.builder()
                .status(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> details = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation ->
                        violation.getPropertyPath().toString(), ConstraintViolation::getMessage));
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        return ResponseEntity.status(errorCode.getHttpStatus()).body(ApiResponse.builder()
                .status(errorCode.getCode())
                .message(errorCode.getMessage()).
                data(details)
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,  // Lấy tên trường bị lỗi
                        FieldError::getDefaultMessage, // Lấy nội dung lỗi
                        (existingValue, newValue) -> existingValue // Nếu có nhiều lỗi cùng 1 field, giữ lỗi đầu tiên
                ));

        return ResponseEntity.badRequest().body(ApiResponse.error(400, "Dữ liệu không hợp lệ", errors));
    }
}
