package com.project.stock_api.dto;

import com.project.stock_api.enums.MovementType;

public record MovementRequestDTO(
    Long product_id,
    float quantity,
    MovementType type
){}
