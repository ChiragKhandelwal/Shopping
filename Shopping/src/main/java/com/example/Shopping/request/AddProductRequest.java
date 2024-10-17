package com.example.Shopping.request;


import com.example.Shopping.model.Category;
import lombok.Data;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private double price;
    private int inventory;
    private String description;
    private Category category;
}