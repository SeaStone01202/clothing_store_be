package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.entity.Order;
import com.java6.asm.clothing_store.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser(User user);

    Page<Order> findAll(Pageable pageable);

    Optional<Order> findById(Integer id);

    @Query(value = "SELECT COUNT(*) FROM orders", nativeQuery = true)
    Integer countTotalOrder();


    @Query(value = "select coalesce(sum(total_price),0) from orders where status = 'delivered' ", nativeQuery = true)
    Double calculateRevenue();
}