package com.java6.asm.clothing_store.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductResponse implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private String imageUrl;

    private Integer stock;

    private Double price;

    private String category;
}
