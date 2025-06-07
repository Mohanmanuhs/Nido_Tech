package com.example.corpCartServer.service;


import com.example.corpCartServer.models.Order;
import com.example.corpCartServer.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;


    public List<Order> getAllOrders() {
        return null;
    }

    public Optional<Object> getOrderById(Long id) {
        return Optional.empty();
    }

    public Order createOrder(Order order) {
        return null;
    }

    public Optional<Object> updateOrder(Long id, Order order) {
        return Optional.empty();
    }

    public boolean deleteOrder(Long id) {
        return false;
    }
}