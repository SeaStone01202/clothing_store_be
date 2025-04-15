package com.java6.asm.clothing_store.dto.request;

import com.java6.asm.clothing_store.constance.StatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    private Integer id;

    @NotNull(message = "Không được để trống tên thể loại")
    @NotEmpty
    private String name;

    private StatusEnum status;
}
