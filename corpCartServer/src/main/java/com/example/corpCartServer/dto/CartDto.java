package com.example.corpCartServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Double totalAmount;

    private List<CartItemDto> cartItemDtos;

}