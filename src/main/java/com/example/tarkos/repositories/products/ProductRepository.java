package com.example.tarkos.repositories.products;

import com.example.tarkos.models.Product;
import com.example.tarkos.models.Task;

import java.util.Collection;
import java.util.List;

public interface ProductRepository {
    Collection<Product> getProducts();
    Collection<String> getProductsTypes();
    void updateCountProductByName(String name, Double count);
    Collection<String> getProductNameByDerivativeProductName(String derivativeProductName);
    void addProductsTasks(List<Task> tasks);
}
