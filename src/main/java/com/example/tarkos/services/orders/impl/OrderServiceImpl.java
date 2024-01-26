package com.example.tarkos.services.orders.impl;


import com.example.tarkos.dtos.OrderDto;
import com.example.tarkos.mappers.OrderMapper;
import com.example.tarkos.models.Order;
import com.example.tarkos.repositories.orders.OrderRepository;
import com.example.tarkos.repositories.products.ProductRepository;
import com.example.tarkos.repositories.users.UserRepository;
import com.example.tarkos.services.orders.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    public void executeOrderById(Integer orderId) {
        log.info("Execute order by id - {}", orderId);
        Order order = orderRepository.findOrderById(orderId)
                .orElseThrow(() -> new NoSuchElementException("No such order by id"));
        productRepository.updateCountProductByName(order.getProductName(), order.getCount());
        orderRepository.updateReadyOrderById(orderId);
    }


    public Collection<OrderDto> getAllOrders() {
        return orderRepository.getAllOrders()
                .stream()
                .map(orderMapper::convertToOrderDto)
                .toList();
    }

    public Collection<String> getAllExecutors() {
        log.info("Getting all executors");
        return orderRepository.getAllExecutors();
    }

    public Collection<OrderDto> findActiveOrdersByProfession(String username) {
        log.info("Searching actual orders by username - {}", username);
        String profession = userRepository.findProfessionByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Username not found"));

        return orderRepository.findActiveOrdersByProfession(profession)
                .stream()
                .map(orderMapper::convertToOrderDto)
                .toList();
    }


    public OrderDto addOrder(OrderDto orderDto) {
        log.info("Adding order");

        Order order = orderRepository.addOrder(
                orderMapper.convertToOrder(orderDto)
        );

        return orderMapper.convertToOrderDto(order);
    }
}
