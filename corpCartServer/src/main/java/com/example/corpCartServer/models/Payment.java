package com.example.corpCartServer.models;

import com.example.corpCartServer.constants.PaymentMethod;
import com.example.corpCartServer.constants.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = false)
    @JsonManagedReference
    private Order order;

    private LocalDateTime paymentDate;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private PaymentMethod paymentMethod;

    @Column(unique = true)
    private String transactionId;
}
