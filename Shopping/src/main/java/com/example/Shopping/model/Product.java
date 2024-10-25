package com.example.Shopping.model;

import com.example.Shopping.dto.ProductDto;
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
    private String brand;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public ProductDto getDto(){
        ProductDto dto=new ProductDto();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setCategory(this.category);
        dto.setPrice(this.price);
        dto.setDescription(this.description);
        dto.setBrand(this.brand);
        dto.setInventory(this.quantity);
        return dto;
    }
}
