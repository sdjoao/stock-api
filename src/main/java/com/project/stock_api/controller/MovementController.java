package com.project.stock_api.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.stock_api.dto.MovementReportDTO;
import com.project.stock_api.dto.MovementRequestDTO;
import com.project.stock_api.dto.MovementResponseDTO;
import com.project.stock_api.services.MovementService;
import com.project.stock_api.services.ReportService;

@RestController
@RequestMapping("/movements")
public class MovementController {
    
    private final MovementService movementService;
    private final ReportService reportService;

    public MovementController(MovementService movementService, ReportService reportService){
        this.movementService = movementService;
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementRequestDTO request){
        return ResponseEntity.status(201).body(movementService.createMovement(request));
    }

     @GetMapping("/reports/stockMovements")
    public ResponseEntity<byte[]> generateReport() throws Exception {

        List<MovementReportDTO> data =
                movementService.getMovementReportData();

        byte[] pdf =
                reportService.generateStockMovementsReport(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=products.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
