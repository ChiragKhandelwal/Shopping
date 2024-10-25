package com.example.Shopping.dto;

import com.example.Shopping.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private double price;
    private int inventory;
    private String description;
    private Category category;
    //
    // private List<ImageDto> images;
}