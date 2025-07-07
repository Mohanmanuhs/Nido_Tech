package com.example.corpCartServer.models;

import com.example.corpCartServer.models.user.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Cart {

    @Id
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "customerId", nullable = false)
    @MapsId
    @JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    public Double getTotalAmount() {
        return cartItems.stream().mapToDouble(item -> item.getProduct().getProductPrice() * item.getCartItemQuantity()).sum();
    }
}
