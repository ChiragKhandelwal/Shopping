package com.example.Shopping.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;
    private List<OrderItemDto> items;
}
