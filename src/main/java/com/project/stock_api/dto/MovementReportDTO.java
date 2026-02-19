package com.project.stock_api.dto;

import java.util.Date;

public class MovementReportDTO {
    
    private Long id;
    private String productName;
    private Float quantity;
    private String type;
    private Date movementDate;

    public MovementReportDTO(Long id, String productName, Float quantity, String type, Date movementDate) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.type = type;
        this.movementDate = movementDate;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Float getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public Date getMovementDate() {
        return movementDate;
    }
}
