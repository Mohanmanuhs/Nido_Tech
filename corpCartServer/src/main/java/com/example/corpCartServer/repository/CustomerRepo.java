package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    User findByEmail(String email);
}