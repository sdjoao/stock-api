package com.project.stock_api.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(
    String name,
    String description,
    BigDecimal price
){}
