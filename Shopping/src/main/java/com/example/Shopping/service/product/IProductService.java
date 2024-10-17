package com.example.Shopping.service.product;

import com.example.Shopping.dto.ProductDto;
import com.example.Shopping.exceptions.ProductNotFoundException;
import com.example.Shopping.model.Product;
import com.example.Shopping.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id) throws ProductNotFoundException;
    void deleteProductById(Long id);
    //Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
