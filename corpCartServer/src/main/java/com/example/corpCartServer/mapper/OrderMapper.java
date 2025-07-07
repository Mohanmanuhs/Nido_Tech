package com.example.corpCartServer.mapper;

import com.example.corpCartServer.dto.OrderDetailsDto;
import com.example.corpCartServer.dto.OrderItemDto;
import com.example.corpCartServer.dto.OrderResponseDto;
import com.example.corpCartServer.models.Order;
import com.example.corpCartServer.models.OrderItem;

import java.util.List;

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

    public static OrderDetailsDto orderToDto(Order order, OrderDetailsDto dto) {
        if (dto==null || order == null) return null;
        OrderResponseDto orderResponseDto = orderToDto(order,new OrderResponseDto());
        dto.setOrderDto(orderResponseDto);
        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(orderItem ->
                orderItemToDto(orderItem,new OrderItemDto())
        ).toList();
        dto.setOrderItemDtos(orderItemDtos);
        return dto;
    }

    private static OrderItemDto orderItemToDto(OrderItem orderItem, OrderItemDto dto) {
        if (dto==null || orderItem == null) return null;
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setOrderItemQuantity(orderItem.getOrderItemQuantity());
        dto.setOrderItemPrice(orderItem.getOrderItemPrice());
        dto.setProductName(orderItem.getProduct().getProductName());
        dto.setProductImageUrl(orderItem.getProduct().getProductImageUrl());
        dto.setProductId(orderItem.getProduct().getProductId());
        return dto;
    }
}
