package com.example.tarkos.mappers;


import com.example.tarkos.dtos.OrderDto;
import com.example.tarkos.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDto convertToOrderDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDto.OrderDtoBuilder dto = OrderDto.builder();

        return dto.id(order.getId())
                .owner(order.getOwner())
                .executor(order.getExecutor())
                .productName(order.getProductName())
                .count(order.getCount())
                .description(order.getDescription())
                .ready(order.getReady())
                .build();
    }

    public Order convertToOrder(OrderDto dto) {
        if (dto == null) {
            return null;
        }

        Order.OrderBuilder order = Order.builder();

        return order.owner(dto.getOwner())
                .executor(dto.getExecutor())
                .productName(dto.getProductName())
                .count(dto.getCount())
                .description(dto.getDescription())
                .ready(dto.getReady())
                .build();
    }
}
