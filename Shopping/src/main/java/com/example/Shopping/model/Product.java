package com.example.Shopping.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Builder
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String name;
    private double price;
    private int quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    //public Pro
}
