package com.example.Shopping.repository;

import com.example.Shopping.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>  {
}
