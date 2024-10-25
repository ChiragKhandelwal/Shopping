package com.example.Shopping.controller;


import com.example.Shopping.dto.OrderDto;
import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.Order;
import com.example.Shopping.response.ApiResponse;
import com.example.Shopping.service.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order =  orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        List<OrderDto> order = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
    }
}