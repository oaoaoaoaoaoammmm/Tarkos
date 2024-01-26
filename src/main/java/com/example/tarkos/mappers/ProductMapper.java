package com.example.tarkos.mappers;


import com.example.tarkos.dtos.ProductDto;
import com.example.tarkos.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto convertToProductDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto.ProductDtoBuilder dto = ProductDto.builder();

        return dto.id(product.getId())
                .type(product.getType())
                .name(product.getName())
                .amount(product.getAmount())
                .requiredAmount(product.getRequiredAmount())
                .variety(product.getVariety())
                .description(product.getDescription())
                .build();
    }

    public Product convertToProduct(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        return product.name(dto.getName())
                .amount(dto.getAmount())
                .build();
    }
}
