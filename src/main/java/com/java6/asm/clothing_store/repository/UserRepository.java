package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndStatus(String email, StatusEnum status);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> findAll();
}
