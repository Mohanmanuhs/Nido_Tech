package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByProductName(String productName);
}