package com.java6.asm.clothing_store.dto.response;

import com.java6.asm.clothing_store.constance.StatusEnum;
import lombok.Data;

@Data
public class CategoryResponse {

    private Integer id;

    private String name;

    private StatusEnum status;

}
