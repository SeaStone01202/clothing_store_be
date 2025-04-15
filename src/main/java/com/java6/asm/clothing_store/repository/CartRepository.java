package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.entity.Cart;
import com.java6.asm.clothing_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT c FROM Cart c WHERE c.user.email = :email")
    Optional<Cart> findByUserEmail(@Param("email") String email);

    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.user " +
            "LEFT JOIN FETCH c.cartDetails cd " +
            "LEFT JOIN FETCH cd.product p " +
            "LEFT JOIN FETCH p.category " +
            "WHERE c.user = :user")
    Optional<Cart> findByUser(@Param("user") User user);
}
