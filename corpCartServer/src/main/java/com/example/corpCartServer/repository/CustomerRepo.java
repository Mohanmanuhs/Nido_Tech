package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    @Query("SELECT c.cart FROM Customer c WHERE c.email = :email")
    Optional<Cart> findCartByEmail(@Param("email") String email);
}