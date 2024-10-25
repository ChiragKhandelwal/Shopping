package com.example.Shopping.service.Order;

import com.example.Shopping.dto.OrderDto;
import com.example.Shopping.enums.OrderStatus;
import com.example.Shopping.exceptions.ResourceNotFoundException;
import com.example.Shopping.model.Cart;
import com.example.Shopping.model.Order;
import com.example.Shopping.model.OrderItem;
import com.example.Shopping.model.Product;
import com.example.Shopping.repository.CartRepository;
import com.example.Shopping.repository.OrderRepository;
import com.example.Shopping.repository.ProductRepository;
import com.example.Shopping.service.cart.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;
    public Order placeOrder(Long id){

        try {
            Cart cart=cartService.getCart(id);
            Order order=createOrder(cart);

            List<OrderItem> orderItems=getOrderItem(order,cart);
            order.setOrderItems(new HashSet<>(orderItems));
            order.setTotalAmount(getTotal(orderItems));

            Order savedOrder=orderRepository.save(order);
            cartService.clearCart(cart.getId());

            return savedOrder;
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
       // return null;
    }

    public Order getOrder(Long id) throws ResourceNotFoundException {
        return orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }

    public double getTotal(List<OrderItem> list){
         double amt=0;
         for (OrderItem item : list) amt += item.getPrice() * item.getQuantity();
         return amt;
    }


    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }

    private List<OrderItem> getOrderItem(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product=cartItem.getProduct();
            product.setQuantity(product.getQuantity()- cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(order,product,cartItem.getQuantity(), cartItem.getUnitPrice());
        }).toList();
    }

    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::getDto).collect(Collectors.toList());
    }
    public OrderDto getDto(Order order){
        return modelMapper.map(order,OrderDto.class);
    }
}
