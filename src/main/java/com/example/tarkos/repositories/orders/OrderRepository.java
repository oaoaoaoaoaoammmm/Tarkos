package com.example.tarkos.repositories.orders;


import com.example.tarkos.models.Order;

import java.util.Collection;
import java.util.Optional;

public interface OrderRepository {
    Collection<String> getAllExecutors();
    Collection<Order> findActiveOrdersByProfession(String profession);
    Optional<Order> findOrderById(Integer id);
    void updateReadyOrderById(Integer id);
    void addOrder(Order order);
}
