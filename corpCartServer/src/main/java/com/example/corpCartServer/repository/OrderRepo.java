package com.example.corpCartServer.repository;

import com.example.corpCartServer.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

}