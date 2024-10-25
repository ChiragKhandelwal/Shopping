package com.example.Shopping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items;

    public void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {

      for(CartItem item:items){
          double p=item.getUnitPrice();
          int q=item.getQuantity();
          this.amount+=p*q;

      }
    }
}
