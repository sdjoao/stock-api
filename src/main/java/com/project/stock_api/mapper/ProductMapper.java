package com.project.stock_api.mapper;

import java.util.ArrayList;
import java.util.List;

import com.project.stock_api.dto.ProductResponseDTO;
import com.project.stock_api.entity.Product;

public class ProductMapper {
    
    public static ProductResponseDTO conversor(Product product){
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getCreatedAt()
        );
    }

    public static List<ProductResponseDTO> listConversor(List<Product> products){
        List<ProductResponseDTO> dtos = new ArrayList<>();
        for(Product product : products){
            ProductResponseDTO dto = new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCreatedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
