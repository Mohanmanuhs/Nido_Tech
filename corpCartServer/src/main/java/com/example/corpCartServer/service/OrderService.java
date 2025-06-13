package com.example.corpCartServer.service;


import com.example.corpCartServer.constants.OrderStatus;
import com.example.corpCartServer.constants.PaymentStatus;
import com.example.corpCartServer.models.Cart;
import com.example.corpCartServer.models.CartItem;
import com.example.corpCartServer.models.Order;
import com.example.corpCartServer.models.OrderItem;
import com.example.corpCartServer.models.user.Customer;
import com.example.corpCartServer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private ProductRepo productRepo;


    public List<Order> getAllOrders() {
        return null;
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    public Order placeOrder(Long userId) {
        Cart cart = cartRepo.findById(userId).get();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty or does not exist");
        }
        Customer customer = cart.getCustomer();
        Order order = new Order();

        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PLACED);
        order.setTotalAmount(cart.getTotalAmount());
        order.setPaymentStatus(PaymentStatus.PENDING);
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrderItemQuantity(cartItem.getCartItemQuantity());
            orderItem.setOrderItemPrice(cartItem.getProduct().getProductPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setExpectedDeliveryDate(LocalDate.now().plusDays(5));
        return orderRepo.save(order);
    }


    public void updateOrder(Order order) {
        orderRepo.save(order);
    }

    public boolean deleteOrder(Long id) {
        return false;
    }
}