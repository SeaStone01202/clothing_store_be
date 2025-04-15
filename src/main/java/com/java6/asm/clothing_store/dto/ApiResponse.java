package com.java6.asm.clothing_store.dto;

import com.java6.asm.clothing_store.exception.AppException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ApiResponse <T>{

    private int status;

    private String message;

    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> error(AppException appException) {
        return new ApiResponse<>(appException.getErrorCode().getCode(), appException.getErrorCode().getMessage(), null);
    }

    // ✅ Thêm phương thức này để hỗ trợ danh sách lỗi validation
    public static ApiResponse<List<String>> error(List<String> errorMessages) {
        return new ApiResponse<>(400, "Validation Failed", errorMessages);
    }

    public static <T> ApiResponse<T> error(int status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }


}