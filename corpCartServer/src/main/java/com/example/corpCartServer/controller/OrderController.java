package com.example.corpCartServer.controller;


import com.example.corpCartServer.dto.OrderDetailsDto;
import com.example.corpCartServer.dto.OrderResponseDto;
import com.example.corpCartServer.dto.UpdateOrderRequestDto;
import com.example.corpCartServer.models.auth.UserPrincipal;
import com.example.corpCartServer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/forAdmin")
    public ResponseEntity<?> getAllOrdersForAdmin(@AuthenticationPrincipal UserPrincipal userDetails) {
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrdersForCustomers(userDetails);
        return ResponseEntity.ok(orderResponseDtos);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllOrdersForCustomer() {
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrdersForAdmin();
        return ResponseEntity.ok(orderResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        OrderDetailsDto orderResponseDtos = orderService.getOrderDtoById(id);
        return ResponseEntity.ok(orderResponseDtos);
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal UserPrincipal userDetails) {
        OrderResponseDto order = orderService.placeOrder(userDetails);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequestDto updateRequest) {
        orderService.updateOrder(updateRequest);
        return ResponseEntity.ok("Order updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}
