package com.example.Shopping.exceptions;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
