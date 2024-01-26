package com.example.tarkos.controllers;


import com.example.tarkos.dtos.OrderDto;
import com.example.tarkos.services.orders.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/orders")
@Tag(
        name = "Orders controller",
        description = "All about orders."
)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(description = "Get all orders")
    @GetMapping
    public ResponseEntity<Collection<OrderDto>> getAllOrdersForManager() {

        Collection<OrderDto> orders = orderService.getAllOrders();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orders);
    }

    @Operation(description = "Get all executors")
    @GetMapping("/executors")
    public ResponseEntity<Collection<String>> getAllExecutors() {

        Collection<String> executors = orderService.getAllExecutors();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(executors);
    }

    @Operation(description = "Find all active orders by username")
    @GetMapping("/{username}")
    public ResponseEntity<Collection<OrderDto>> findAllActiveOrdersByProfession(
            @PathVariable
            @NotNull
            @Parameter(name = "profession", required = true)
            String username
    ) {
        Collection<OrderDto> orders = orderService.findActiveOrdersByProfession(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orders);
    }

    @Operation(description = "Execute order by id")
    @PutMapping("/{orderId}")
    public ResponseEntity<?> executeOrderById(
            @PathVariable
            @NotNull
            @Parameter(description = "Order id", required = true)
            Integer orderId
    ) {
        orderService.executeOrderById(orderId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(description = "Add order")
    @PostMapping
    public ResponseEntity<OrderDto> addOrder(
        @RequestBody
        @NotNull
        @Parameter(description = "Order dto", required = true)
        OrderDto orderDto
    ) {
        orderDto = orderService.addOrder(orderDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderDto);
    }
}
