package com.project.stock_api.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.stock_api.dto.ProductReportDTO;
import com.project.stock_api.dto.ProductRequestDTO;
import com.project.stock_api.dto.ProductResponseDTO;
import com.project.stock_api.services.ProductService;
import com.project.stock_api.services.ReportService;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;
    private final ReportService reportService;

    public ProductController(ProductService productService, ReportService reportService){
        this.productService = productService;
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request){
        return ResponseEntity.status(201).body(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts(){
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @GetMapping("/reports/products")
    public ResponseEntity<byte[]> generateReport() throws Exception {

        List<ProductReportDTO> data =
                productService.getProductReportData();

        byte[] pdf =
                reportService.generateProductReport(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=products.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductRequestDTO request, @PathVariable Long id){
        return ResponseEntity.ok().body(productService.updateProduct(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
