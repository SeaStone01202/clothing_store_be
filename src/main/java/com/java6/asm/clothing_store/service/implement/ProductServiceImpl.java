package com.java6.asm.clothing_store.service.implement;

import com.java6.asm.clothing_store.dto.mapper.ProductMapper;
import com.java6.asm.clothing_store.dto.request.ProductRequest;
import com.java6.asm.clothing_store.dto.response.ProductResponse;
import com.java6.asm.clothing_store.entity.Product;
import com.java6.asm.clothing_store.repository.ProductRepository;
import com.java6.asm.clothing_store.service.ProductService;
import com.java6.asm.clothing_store.utils.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final PageUtil pageUtil;


    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public void deleteProduct(Integer productId) {

    }

    @Override
    public ProductResponse getProduct(Integer productId) {
        return null;
    }

    @Cacheable(value = "products", key = "'all:page:' + #page")
    @Override
    public Page<ProductResponse> findAll(int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> findByCategory(String category, int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return productRepository.findByCategoryName(category, pageable).map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> findByCategoryAndPrice(Double min, Double max, String category, int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return productRepository.findByCategoryNameAndPriceBetween(min, max, category, pageable).map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> findByPrice(Double min, Double max, int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return productRepository.findByPriceBetween(min, max, pageable).map(productMapper::toResponse);
    }

    @Override
    public Page<ProductResponse> findByName(String keyword, int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable)
                .map(productMapper::toResponse);
    }

    @Override
    public ProductResponse findById(Integer id) {
        Product product = productRepository.findByIdCustom(id);
        return product != null ? productMapper.toResponse(product) : null;
    }

    @Override
    public Page<ProductResponse> findRelatedProducts(String categoryName, Integer excludeId, int page) {
        Pageable pageable = pageUtil.createPageable(page);
        return productRepository.findRelatedProducts(categoryName, excludeId, pageable)
                .map(productMapper::toResponse);
    }

    @Override
    @PreAuthorize("hasAnyRole('STAFF', 'DIRECTOR')")
    public Integer countProductsStock() {
        return productRepository.countAllProducts();
    }

}
