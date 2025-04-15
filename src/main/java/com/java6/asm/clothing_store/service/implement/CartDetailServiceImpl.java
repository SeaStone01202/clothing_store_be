package com.java6.asm.clothing_store.service.implement;

import com.java6.asm.clothing_store.dto.mapper.CartDetailMapper;
import com.java6.asm.clothing_store.dto.response.CartDetailResponse;
import com.java6.asm.clothing_store.entity.Cart;
import com.java6.asm.clothing_store.entity.CartDetail;
import com.java6.asm.clothing_store.entity.Product;
import com.java6.asm.clothing_store.exception.AppException;
import com.java6.asm.clothing_store.exception.ErrorCode;
import com.java6.asm.clothing_store.repository.CartDetailRepository;
import com.java6.asm.clothing_store.repository.CartRepository;
import com.java6.asm.clothing_store.repository.ProductRepository;
import com.java6.asm.clothing_store.service.CartDetailService;
import com.java6.asm.clothing_store.service.authentication.AccessTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {

    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartDetailMapper cartDetailMapper;
    private final AccessTokenService accessTokenService;

    @Transactional
    @Override
    public CartDetailResponse addProductToCart(String accessToken, Integer productId, Integer quantity) {
        // Validate access token
        if (accessToken == null || accessToken.isBlank()) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        String email = accessTokenService.validateToken(accessToken);
        if (email == null) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

        // Validate quantity
        if (quantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        // Tìm cart của user
        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        // Tìm product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        // Kiểm tra tồn kho
        if (product.getStock() < quantity) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        // Tìm CartDetail hiện có
        CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartDetail != null) {
            // Kiểm tra tồn kho khi tăng số lượng
            int newQuantity = cartDetail.getQuantity() + quantity;
            if (product.getStock() < newQuantity) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }
            cartDetail.setQuantity(newQuantity);
        } else {
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setQuantity(quantity);
        }

        cartDetail = cartDetailRepository.save(cartDetail);
        return cartDetailMapper.toResponse(cartDetail);
    }

    @Transactional
    @Override
    public CartDetailResponse updateQuantity(String accessToken, Integer cartDetailId, Integer quantity) {
        // Validate access token
        if (accessToken == null || accessToken.isBlank()) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        String email = accessTokenService.validateToken(accessToken);
        if (email == null) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

        // Validate quantity
        if (quantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        // Tìm CartDetail
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        // Kiểm tra quyền truy cập
        if (!cartDetail.getCart().getUser().getEmail().equals(email)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // Kiểm tra tồn kho
        Product product = cartDetail.getProduct();
        if (product.getStock() < quantity) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        cartDetail.setQuantity(quantity);
        cartDetail = cartDetailRepository.save(cartDetail);
        return cartDetailMapper.toResponse(cartDetail);
    }

    @Transactional
    @Override
    public boolean deleteCartDetail(Integer cartDetailId) {

        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).orElse(null);
        if (cartDetail == null) {
            return false;
        }

        cartDetailRepository.delete(cartDetail);

        return true;
    }
}



