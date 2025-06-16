package com.example.corpCartServer.service;


import com.example.corpCartServer.constants.OrderStatus;
import com.example.corpCartServer.constants.PaymentStatus;
import com.example.corpCartServer.dto.OrderResponseDto;
import com.example.corpCartServer.dto.UpdateOrderRequestDto;
import com.example.corpCartServer.exception.ResourceNotFoundException;
import com.example.corpCartServer.mapper.OrderMapper;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.CartItem;
import com.example.corpCartServer.models.Order;
import com.example.corpCartServer.models.OrderItem;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;

    public List<OrderResponseDto> getAllOrdersForCustomers(UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<Order> orders = customerRepo.findOrdersByEmail(email);
        return orders.stream().map(
                order -> OrderMapper.orderToDto(order,new OrderResponseDto())
        ).toList();
    }

    public List<OrderResponseDto> getAllOrdersForAdmin() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(
                order -> OrderMapper.orderToDto(order,new OrderResponseDto())
        ).toList();
    }

    public OrderResponseDto getOrderDtoById(Long id) {
        Order order = getOrderById(id);

        return OrderMapper.orderToDto(
                order,new OrderResponseDto()
        );
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("order not found with this id");
        }
        return order.get();
    }

    public OrderResponseDto placeOrder(UserDetails userDetails) {
        String email = userDetails.getUsername();
        Optional<Cart> cart = customerRepo.findCartByEmail(email);

        if (cart.isEmpty() || cart.get().getCartItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty or does not exist");
        }

        Customer customer = cart.get().getCustomer();
        Order order = new Order();

        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setTotalAmount(cart.get().getTotalAmount());
        order.setPaymentStatus(PaymentStatus.PENDING);
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.get().getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrderItemQuantity(cartItem.getCartItemQuantity());
            orderItem.setOrderItemPrice(cartItem.getProduct().getProductPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setExpectedDeliveryDate(LocalDate.now().plusDays(5));
        Order savedOrder = orderRepo.save(order);
        return OrderMapper.orderToDto(savedOrder,new OrderResponseDto());
    }

    public void updateOrder(UpdateOrderRequestDto dto) {
        Order order = getOrderById(dto.getOrderId());

        if (dto.getOrderStatus() != null) {
            order.setOrderStatus(dto.getOrderStatus());
        }
        if (dto.getPaymentStatus() != null) {
            order.setPaymentStatus(dto.getPaymentStatus());
        }
        if (dto.getExpectedDeliveryDate() != null) {
            order.setExpectedDeliveryDate(dto.getExpectedDeliveryDate());
        }
        orderRepo.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orderRepo.delete(order);
    }
}