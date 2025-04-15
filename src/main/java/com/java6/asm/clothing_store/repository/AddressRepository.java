package com.java6.asm.clothing_store.repository;

import com.java6.asm.clothing_store.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("select a from Address a where a.user.email = :email")
    List<Address> findAllByUserEmail(@Param("email") String email);
}