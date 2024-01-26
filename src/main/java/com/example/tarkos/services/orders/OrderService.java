package com.example.tarkos.services.orders;

import com.example.tarkos.dtos.OrderDto;

import java.util.Collection;

public interface OrderService {
    void executeOrderById(Integer orderId);

    Collection<OrderDto> getAllOrders();
    Collection<String> getAllExecutors();
    Collection<OrderDto> findActiveOrdersByProfession(String profession);
    OrderDto addOrder(OrderDto orderDto);
}
