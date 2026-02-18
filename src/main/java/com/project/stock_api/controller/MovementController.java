package com.project.stock_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.stock_api.dto.MovementRequestDTO;
import com.project.stock_api.dto.MovementResponseDTO;
import com.project.stock_api.services.MovementService;

@RestController
@RequestMapping("/movements")
public class MovementController {
    
    private final MovementService movementService;

    public MovementController(MovementService movementService){
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementRequestDTO request){
        return ResponseEntity.status(201).body(movementService.createMovement(request));
    }
}
