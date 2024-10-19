package com.example.Shopping.service.category;

import com.example.Shopping.exceptions.AlreadyExistException;
import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.Category;
import com.example.Shopping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
   CategoryRepository categoryRepository;


    public Category getCategoryById(Long id) throws ResourceNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }


    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
    public Category update(Category c,Long id) throws ResourceNotFoundException{
        return Optional.ofNullable(getCategoryById(id))
                .map(old->{
                    old.setName(c.getName());
                   return categoryRepository.save(old);
                }).orElseThrow(()->new ResourceNotFoundException("Category not found"));
    }

    public Category addCategory(Category category) throws AlreadyExistException {
        return Optional.of(category).filter(c->!categoryRepository.existByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()->new AlreadyExistException("Category exist with given name"));
    }

}
