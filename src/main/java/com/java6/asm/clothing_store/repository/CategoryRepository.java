package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    List<Category> findByStatus(StatusEnum status);
}
