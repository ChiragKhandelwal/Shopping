package com.example.Shopping.service.product;

import com.example.Shopping.dto.ProductDto;
import com.example.Shopping.exceptions.ProductNotFoundException;
import com.example.Shopping.model.Category;
import com.example.Shopping.model.Product;

import com.example.Shopping.repository.CategoryRepository;
import com.example.Shopping.repository.ProductRepository;
import com.example.Shopping.request.AddProductRequest;
import com.example.Shopping.request.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        Category c = getCategory(request.getCategory().getName());

        Product product =Product.builder().
        name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .category(c)
                .quantity(request.getInventory())

                .build();

      return productRepository.save(product);



    }

    private Category getCategory(String name) {
        Category c=categoryRepository.findByName(name).orElse(null);
        if(c==null){
            Category newCategory = new Category();
            newCategory.setName(name);
           c= categoryRepository.save(newCategory);
        }
        return c;
    }

    public Product updateProduct (ProductUpdateRequest request, Long productId) throws ProductNotFoundException {
       Product exist=productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Invalid id"));
       Category c=getCategory(request.getCategory().getName());
       exist.setName(request.getName());
       exist.setCategory(c);
       exist.setDescription(request.getDescription());
       exist.setQuantity(request.getInventory());
       exist.setPrice(request.getPrice());


      return productRepository.save(exist);

    }


    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("No product with given id"));

        //return null;
    }

    @Override
    public void deleteProductById(Long id) {
productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String category, String name) {
        return productRepository.findByBrandAndName(category, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {

        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return null;
        //return products.stream().map();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        return null;
    }
}
