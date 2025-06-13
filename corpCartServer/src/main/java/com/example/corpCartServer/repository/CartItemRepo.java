package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartItemIdAndCart_Customer_Email(Long cartItemId, String email);

}