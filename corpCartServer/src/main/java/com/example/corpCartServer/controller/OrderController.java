package com.example.corpCartServer.controller;


import com.example.corpCartServer.constants.OrderStatus;
import com.example.corpCartServer.constants.PaymentStatus;
import com.example.corpCartServer.dto.OrderResponseDto;
import com.example.corpCartServer.dto.UpdateOrderRequestDto;
import com.example.corpCartServer.mapper.OrderMapper;
import com.example.corpCartServer.models.Order;
import com.example.corpCartServer.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("order/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders().stream().map(order -> OrderMapper.orderToDto(order, new OrderResponseDto())).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(OrderMapper.orderToDto(orderService.getOrderById(id).get(), new OrderResponseDto()));
    }

    @PostMapping("/{userId}/place")
    public ResponseEntity<Order> placeOrder(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderRequestDto updateRequest) {

        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with ID: " + orderId);
        }
        Order order = optionalOrder.get();

        try {
            if (updateRequest.getOrderStatus() != null) {
                order.setOrderStatus(OrderStatus.valueOf(updateRequest.getOrderStatus().toUpperCase()));
            }
            if (updateRequest.getPaymentStatus() != null) {
                order.setPaymentStatus(PaymentStatus.valueOf(updateRequest.getPaymentStatus().toUpperCase()));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid enum value provided.");
        }

        if (updateRequest.getExpectedDeliveryDate() != null) {
            order.setExpectedDeliveryDate(updateRequest.getExpectedDeliveryDate());
        }

        orderService.updateOrder(order);
        return ResponseEntity.ok("Order updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
