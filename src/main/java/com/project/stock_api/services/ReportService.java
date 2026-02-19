package com.project.stock_api.services;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.stock_api.dto.MovementReportDTO;
import com.project.stock_api.dto.ProductReportDTO;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

    public byte[] generateProductReport(List<ProductReportDTO> data) throws Exception {

        InputStream reportStream =
                getClass().getResourceAsStream("/reports/products.jrxml");

        JasperReport jasperReport =
                JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(data);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        new HashMap<>(),
                        dataSource
                );

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] generateStockMovementsReport(List<MovementReportDTO> data) throws Exception {

        InputStream reportStream =
                getClass().getResourceAsStream("/reports/stock_movements.jrxml");

        InputStream logoStream =
            getClass().getResourceAsStream("/reports/logo.png");

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("LOGO", logoStream);

        JasperReport jasperReport =
                JasperCompileManager.compileReport(reportStream);

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(data);

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        parameters,
                        dataSource
                );

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
