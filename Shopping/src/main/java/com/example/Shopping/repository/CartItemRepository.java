package com.example.Shopping.repository;

import com.example.Shopping.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    public void deleteAllByCartId(Long id);
}
