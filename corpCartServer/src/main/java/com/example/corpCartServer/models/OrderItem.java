package com.example.corpCartServer.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonManagedReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonManagedReference
    private Product product;

    private int orderItemQuantity;

    private double orderItemPrice;
}
