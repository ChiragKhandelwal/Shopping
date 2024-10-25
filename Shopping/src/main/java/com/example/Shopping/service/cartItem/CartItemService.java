package com.example.Shopping.service.cartItem;

import com.example.Shopping.exceptions.ProductNotFoundException;
import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.Cart;
import com.example.Shopping.model.CartItem;
import com.example.Shopping.model.Product;
import com.example.Shopping.repository.CartItemRepository;
import com.example.Shopping.repository.CartRepository;
import com.example.Shopping.service.cart.CartService;
import com.example.Shopping.service.product.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    public void addItemTOCart(Long cartId,Long productId,int quantity) throws ResourceNotFoundException, ProductNotFoundException {

        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem item=cart.getItems().stream().filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());

    if(item.getId()==null){
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setTotalPrice();
        item.setUnitPrice(product.getPrice());
        item.setCart(cart);
    }
    else{
        item.setQuantity(item.getQuantity()+quantity);
        item.setTotalPrice();
    }
    cart.addItem(item);
    cartItemRepository.save(item);
    cartRepository.save(cart);

    }
    public void removeItemFromCart(Long cartId, Long productId) throws ResourceNotFoundException, ProductNotFoundException {
        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem item=cart.getItems().
                stream().
                filter(cartItem -> cartItem.getProduct().getId().equals(productId)).
                findFirst().
                orElse(new CartItem());
cart.removeItem(item);

    }

    public void updateItemQuantity(Long cartId, Long productId, int quantity) throws ResourceNotFoundException, ProductNotFoundException {
        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(productId);
        CartItem item;cart.getItems().
                stream().
                filter(cartItem -> cartItem.getProduct().getId().equals(productId)).
                findFirst().
               ifPresent(cartItem -> {
                   cartItem.setQuantity(quantity);
                   cartItem.setUnitPrice(product.getPrice());
                   cartItem.setTotalPrice();
               });

        double total=0;
        for(CartItem item1:cart.getItems()){
            total+=item1.getUnitPrice()* item1.getQuantity();
        }
        cart.setAmount(total);
        cartRepository.save(cart);

    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = null;
        try {
            cart = cartService.getCart(cartId);

            return  cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
