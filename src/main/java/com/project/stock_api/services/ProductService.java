package com.project.stock_api.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.stock_api.dto.ProductReportDTO;
import com.project.stock_api.dto.ProductRequestDTO;
import com.project.stock_api.dto.ProductResponseDTO;
import com.project.stock_api.entity.Product;
import com.project.stock_api.exceptions.BusinessException;
import com.project.stock_api.mapper.ProductMapper;
import com.project.stock_api.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    // método privado para encontrar e retornar o produto para os demais métodos.
    public Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new BusinessException("Produto de ID " + id + " não encontrado."));
    }

    public void updateProductQuantity(Long id, float quantity){
        Product product = findProductById(id);
        product.setQuantity(quantity);
        productRepository.save(product);
    }

    private boolean productIsValid(ProductRequestDTO request){
        if(request.name() == null || request.name().isBlank()){
            throw new BusinessException("Nome do produto vazio ou inválido.");
        }
        if(request.description() == null || request.description().isBlank()){
            throw new BusinessException("Descrição do produto vazia ou inválida.");
        }
        if(request.price() == null ||request.price().compareTo(BigDecimal.ZERO) < 0){
            throw new BusinessException("Produto com valor inválido, não é permitido valor negativo ou vazio.");
        }
        return true;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO request){
        productIsValid(request);
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        productRepository.save(product);
        return ProductMapper.conversor(product);
    }

    public List<ProductResponseDTO> getProducts(){
        return ProductMapper.listConversor(productRepository.findAll());
    }

    public ProductResponseDTO getProductById(Long id){
        return ProductMapper.conversor(findProductById(id));   
    }

    public ProductResponseDTO updateProduct(ProductRequestDTO request, Long id){
        Product product = findProductById(id);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        productRepository.save(product);
        return ProductMapper.conversor(product);
    }

    public void deleteProduct(Long id){
        productRepository.delete(findProductById(id));
    }

    // relatório de produtos
    public List<ProductReportDTO> getProductReportData(){
        return productRepository.findAll()
            .stream()
            .map(product ->  new ProductReportDTO(
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()))
            ))
            .toList();
    }
}
