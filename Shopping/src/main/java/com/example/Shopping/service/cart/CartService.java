package com.example.Shopping.service.cart;

import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.Cart;
import com.example.Shopping.model.CartItem;
import com.example.Shopping.model.Order;
import com.example.Shopping.repository.CartItemRepository;
import com.example.Shopping.repository.CartRepository;
import com.example.Shopping.repository.OrderRepository;
import com.example.Shopping.dto.OrderDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
@Autowired
    OrderRepository orderRepository;
    @Autowired
    ModelMapper modelMapper;


   // @Autowired
    AtomicLong cartIdGenerator=new AtomicLong(0);


    public Cart getCart(Long id) throws ResourceNotFoundException {
        Cart cart= cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No cart with given id"+id));

        cart.setAmount(cart.getAmount());
        return cart;

    }
@Transactional
    public void clearCart(Long id) throws ResourceNotFoundException {
        Cart cart=getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
    }
    public double getTotalPrice(Long id) throws ResourceNotFoundException {
        Cart cart=getCart(id);
        double amt=0;
        for(CartItem item: cart.getItems()){
            amt+= item.getTotalPrice();
        }
        return amt;
    }

    public Long initializeNewCart() {
        Cart cart=new Cart();
        long id=cartIdGenerator.incrementAndGet();
        cart.setId(id);
        return cartRepository.save(cart).getId();

    }
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return  orders.stream().map(this :: convertToDto).toList();
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
