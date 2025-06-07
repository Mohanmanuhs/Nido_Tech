package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Optional<Cart> findByCustomer_UserId(Long customerId);

}