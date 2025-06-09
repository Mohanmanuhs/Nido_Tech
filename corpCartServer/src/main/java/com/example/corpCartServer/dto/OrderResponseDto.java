package com.example.corpCartServer.dto;

import com.example.corpCartServer.constants.OrderStatus;
import com.example.corpCartServer.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;

    private LocalDateTime orderDate;

    private double totalAmount;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private LocalDate expectedDeliveryDate;
}