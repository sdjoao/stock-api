package com.project.stock_api.dto;

import java.time.LocalDateTime;

import com.project.stock_api.enums.MovementType;

public record MovementResponseDTO(
    Long id,
    Long product_id,
    float quantity,
    MovementType type,
    LocalDateTime movementDate
){}
