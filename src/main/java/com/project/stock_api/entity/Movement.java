package com.project.stock_api.entity;

import java.time.LocalDateTime;

import com.project.stock_api.enums.MovementType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Movement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id") // chave FK na tabela moviment
    private Product product;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(name = "quantity", nullable = false)
    private float quantity;

    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate;

    @PrePersist
    public void prePersist(){
        this.movementDate = LocalDateTime.now();
    }

    public Movement(){} // construtor vazio para o framework

    public Movement(Product product, MovementType type, float quantity){
        this.product = product;
        this.type = type;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    
}
