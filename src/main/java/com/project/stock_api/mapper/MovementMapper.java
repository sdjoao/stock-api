package com.project.stock_api.mapper;

import java.util.ArrayList;
import java.util.List;

import com.project.stock_api.dto.MovementResponseDTO;
import com.project.stock_api.entity.Movement;

public class MovementMapper {
    
    public static MovementResponseDTO conversor(Movement movement){
        return new MovementResponseDTO(
            movement.getId(),
            movement.getProduct().getId(),
            movement.getQuantity(),
            movement.getType(),
            movement.getMovementDate());
    }

    public static List<MovementResponseDTO> listConversor(List<Movement> movements){
        List<MovementResponseDTO> dtos = new ArrayList<>();
        for(Movement movement : movements){
            MovementResponseDTO dto = new MovementResponseDTO(
                movement.getId(),
                movement.getProduct().getId(),
                movement.getQuantity(),
                movement.getType(),
                movement.getMovementDate()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
