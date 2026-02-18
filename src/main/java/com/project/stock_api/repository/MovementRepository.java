package com.project.stock_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.stock_api.entity.Movement;


@Repository
public interface MovementRepository extends JpaRepository<Movement, Long>{
    
}
