package com.java6.asm.clothing_store.service;

import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.dto.request.ProductRequest;
import com.java6.asm.clothing_store.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);

    ProductResponse updateProduct(ProductRequest productRequest);

    void deleteProduct(Integer productId);

    ProductResponse getProduct(Integer productId);

    Page<ProductResponse> findAll(int page);

    Page<ProductResponse> findByCategory(String category, int pageable);

    Page<ProductResponse> findByName(String name, int pageable);

    Page<ProductResponse> findByCategoryAndPrice(Double min, Double max, String category, int pageable);

    Page<ProductResponse> findByPrice(Double min, Double max, int pageable);

    ProductResponse findById(Integer id);

    Page<ProductResponse> findRelatedProducts(String categoryName, Integer excludeId, int page);

    Integer countProductsStock();
}
