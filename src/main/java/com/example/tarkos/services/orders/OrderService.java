package com.example.tarkos.services.orders;

import com.example.tarkos.dtos.OrderDto;

import java.util.Collection;

public interface OrderService {
    void executeOrderById(Integer orderId);
    Collection<String> getAllExecutors();
    Collection<OrderDto> findActiveOrdersByProfession(String profession);
    void addOrder(OrderDto orderDto);
}
