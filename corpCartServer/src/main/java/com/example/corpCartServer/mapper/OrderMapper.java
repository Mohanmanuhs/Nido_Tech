package com.example.corpCartServer.mapper;

import com.example.corpCartServer.dto.OrderResponseDto;
import com.example.corpCartServer.models.Order;

public class OrderMapper {

    public static OrderResponseDto orderToDto(Order order, OrderResponseDto dto) {
        if (dto==null || order == null) return null;
        dto.setOrderId(order.getOrderId());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        return dto;
    }
}
