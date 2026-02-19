package com.project.stock_api.dto;

import java.math.BigDecimal;

public class ProductReportDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Float quantity;
    private BigDecimal totalValue;

    public ProductReportDTO(String name, String description,
                            BigDecimal price, Float quantity,
                            BigDecimal totalValue) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Float getQuantity() { return quantity; }
    public BigDecimal getTotalValue() { return totalValue; }
}

