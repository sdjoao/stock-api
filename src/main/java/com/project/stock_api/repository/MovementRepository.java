package com.project.stock_api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.stock_api.entity.Movement;
import com.project.stock_api.entity.Product;


@Repository
public interface MovementRepository extends JpaRepository<Movement, Long>{
    
    List<Movement> findByMovementDateBetween(LocalDate start, LocalDate end);

    List<Movement> findMovementByProduct(Product product);
}
