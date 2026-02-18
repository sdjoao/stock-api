package com.project.stock_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    float quantity,
    LocalDateTime createdAt
){}
