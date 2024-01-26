package com.example.tarkos.services.products.impl;


import com.example.tarkos.dtos.ProductDto;
import com.example.tarkos.mappers.ProductMapper;
import com.example.tarkos.repositories.products.ProductRepository;
import com.example.tarkos.services.products.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Collection<ProductDto> getProducts() {
        log.debug("Getting products");

        return productRepository.getProducts()
                .stream()
                .map(productMapper::convertToProductDto)
                .toList();
    }

    public Collection<String> getProductsTypes() {
        log.info("Getting products types");

        return productRepository.getProductsTypes();
    }
}
