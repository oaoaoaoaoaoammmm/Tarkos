package com.example.tarkos.services.products;

import com.example.tarkos.dtos.ProductDto;

import java.util.Collection;

public interface ProductService {
    Collection<ProductDto> getProducts();
    Collection<String> getProductsTypes();
}
