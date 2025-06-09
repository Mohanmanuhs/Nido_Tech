package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequestDto {
    private String orderStatus;
    private String paymentStatus;
    private LocalDate expectedDeliveryDate;
}