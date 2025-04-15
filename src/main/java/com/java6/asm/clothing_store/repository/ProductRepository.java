package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    Page<Product> findByCategoryName(
            @Param("categoryName") String categoryName,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.price BETWEEN :min AND :max")
    Page<Product> findByCategoryNameAndPriceBetween(
            @Param("min") Double min,
            @Param("max") Double max,
            @Param("categoryName") String categoryName,
            Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE price BETWEEN :min AND :max", nativeQuery = true)
    Page<Product> findByPriceBetween(
            @Param("min") Double min,
            @Param("max") Double max,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByNameContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findByIdCustom(
            @Param("id") Integer id);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.id != :excludeId")
    Page<Product> findRelatedProducts(
            @Param("categoryName") String categoryName,
            @Param("excludeId") Integer excludeId,
            Pageable pageable);

    @Query(value = "SELECT SUM(stock) FROM products", nativeQuery = true)
    Integer countAllProducts();
}
