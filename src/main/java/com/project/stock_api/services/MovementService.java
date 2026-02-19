package com.project.stock_api.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.stock_api.dto.MovementReportDTO;
import com.project.stock_api.dto.MovementRequestDTO;
import com.project.stock_api.dto.MovementResponseDTO;
import com.project.stock_api.dto.ProductResponseDTO;
import com.project.stock_api.entity.Movement;
import com.project.stock_api.entity.Product;
import com.project.stock_api.enums.MovementType;
import com.project.stock_api.exceptions.BusinessException;
import com.project.stock_api.mapper.MovementMapper;
import com.project.stock_api.repository.MovementRepository;

@Service
public class MovementService {
    
    private final MovementRepository movementRepository;
    private final ProductService productService;

    public MovementService(MovementRepository movementRepository, ProductService productService){
        this.movementRepository = movementRepository;
        this.productService = productService;
    }

    // método privado que encontra e retorna o movimento para os demais métodos.
    private Movement findMovementById(Long id){
        return movementRepository.findById(id).orElseThrow(() -> new BusinessException("Movimento com ID " + id + " não encontrado."));
    }

    // método privado que verifica os campos da requisição.
    private boolean movementIsValid(MovementRequestDTO request){
        if(request.quantity() <= 0){
            throw new BusinessException("Movimento inválido, quantidade não pode ser zero ou negativa.");
        }
        ProductResponseDTO dto = productService.getProductById(request.product_id());
        if(request.type().equals(MovementType.OUT) && request.quantity() > dto.quantity()){
            throw new BusinessException("Movimento inválido, quantidade solicitada deixará o estoque negativo.");
        }
        return true;
    } 

    public float getProductQuantity(MovementRequestDTO request){
        List<MovementResponseDTO> dtos = getMovementsByProduct(request);
        float inMovement = 0;
        float outMovement = 0;
        float actualQuantity = 0;
        for(MovementResponseDTO movement : dtos){
            if(movement.type().equals(MovementType.IN)){
                inMovement += movement.quantity();
            }
            if(movement.type().equals(MovementType.OUT)){
                outMovement += movement.quantity();
            }
        }
        actualQuantity = inMovement - outMovement;
        return actualQuantity;
    }

    public MovementResponseDTO createMovement(MovementRequestDTO requestDTO){
        movementIsValid(requestDTO);
        Movement movement = new Movement();
        Product product = productService.findProductById(requestDTO.product_id());
        movement.setProduct(product);
        movement.setQuantity(requestDTO.quantity());
        movement.setType(requestDTO.type());
        movementRepository.save(movement);
        productService.updateProductQuantity(requestDTO.product_id(), getProductQuantity(requestDTO));
        return MovementMapper.conversor(movement);
    }

    public List<MovementResponseDTO> getMovements(){
        return MovementMapper.listConversor(movementRepository.findAll());
    }

    public List<MovementResponseDTO> getMovementsByDate(LocalDate start, LocalDate end){
        return MovementMapper.listConversor(movementRepository.findByMovementDateBetween(start, end));
    }

    public List<MovementResponseDTO> getMovementsByProduct(MovementRequestDTO request){
        Product product = productService.findProductById(request.product_id());
        return MovementMapper.listConversor(movementRepository.findMovementByProduct(product));
    }

    // relatório de movimentos de estoque
    public List<MovementReportDTO> getMovementReportData(){
        return movementRepository.findAll()
            .stream()
            .map(movement ->  new MovementReportDTO(
                    movement.getId(),
                    movement.getProduct().getName(),
                    movement.getQuantity(),
                    movement.getType().name(),
                    Date.from(movement.getMovementDate()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                ))
            )
            .toList();
    }

}
