package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.entity.Cart;
import com.java6.asm.clothing_store.entity.CartDetail;
import com.java6.asm.clothing_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

    @Query("SELECT cd FROM CartDetail cd WHERE cd.cart = :cart AND cd.product = :product")
    Optional<CartDetail> findByCartAndProduct(@Param("cart") Cart cart, @Param("product") Product product);

    Optional<CartDetail> deleteByCart(Cart cart);

}