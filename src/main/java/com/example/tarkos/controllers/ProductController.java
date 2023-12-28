package com.example.tarkos.controllers;


import com.example.tarkos.dtos.ProductDto;
import com.example.tarkos.services.products.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@Validated
@RestController
@RequestMapping("/products")
@Tag(
        name = "Products controller",
        description = "All about product."
)
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService
    ) {
        this.productService = productService;
    }


    @Operation(summary = "Get all products")
    @GetMapping
    public ResponseEntity<Collection<ProductDto>> getProducts() {
        Collection<ProductDto> products = productService.getProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(products);
    }

    @Operation(summary = "Get all product's types")
    @GetMapping("/types")
    public ResponseEntity<Collection<String>> getProductsTypes() {
        Collection<String> collection = productService.getProductsTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collection);
    }
}
