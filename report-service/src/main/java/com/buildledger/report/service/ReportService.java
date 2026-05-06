package com.buildledger.report.service;

import com.buildledger.report.feign.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ProjectServiceClient projectServiceClient;
    private final ContractServiceClient contractServiceClient;
    private final FinanceServiceClient financeServiceClient;
    private final DeliveryServiceClient deliveryServiceClient;

    public Map<String, Object> generateProjectReport() {
        log.info("Generating project report");
        Map<String, Object> report = new LinkedHashMap<>();
        try {
            Map<String, Object> projects = projectServiceClient.getAllProjects();
            report.put("projects", projects.getOrDefault("data", Collections.emptyList()));
            report.put("success", true);
            report.put("message", "Project report generated successfully");
        } catch (Exception e) {
            log.error("Error generating project report: {}", e.getMessage());
            report.put("success", false);
            report.put("message", "Project Service is currently unavailable");
        }
        return report;
    }

    public Map<String, Object> generateContractReport() {
        log.info("Generating contract report");
        Map<String, Object> report = new LinkedHashMap<>();
        try {
            Map<String, Object> contracts = contractServiceClient.getAllContracts();
            report.put("contracts", contracts.getOrDefault("data", Collections.emptyList()));
            report.put("success", true);
            report.put("message", "Contract report generated successfully");
        } catch (Exception e) {
            log.error("Error generating contract report: {}", e.getMessage());
            report.put("success", false);
            report.put("message", "Contract Service is currently unavailable");
        }
        return report;
    }

    public Map<String, Object> generateFinancialReport() {
        log.info("Generating financial report");
        Map<String, Object> report = new LinkedHashMap<>();
        try {
            Map<String, Object> invoices = financeServiceClient.getAllInvoices();
            Map<String, Object> payments = financeServiceClient.getAllPayments();
            report.put("invoices", invoices.getOrDefault("data", Collections.emptyList()));
            report.put("payments", payments.getOrDefault("data", Collections.emptyList()));
            report.put("success", true);
            report.put("message", "Financial report generated successfully");
        } catch (Exception e) {
            log.error("Error generating financial report: {}", e.getMessage());
            report.put("success", false);
            report.put("message", "Finance Service is currently unavailable");
        }
        return report;
    }

    public Map<String, Object> generateDeliveryReport() {
        log.info("Generating delivery report");
        Map<String, Object> report = new LinkedHashMap<>();
        try {
            Map<String, Object> deliveries = deliveryServiceClient.getAllDeliveries();
            report.put("deliveries", deliveries.getOrDefault("data", Collections.emptyList()));
            report.put("success", true);
            report.put("message", "Delivery report generated successfully");
        } catch (Exception e) {
            log.error("Error generating delivery report: {}", e.getMessage());
            report.put("success", false);
            report.put("message", "Delivery Service is currently unavailable");
        }
        return report;
    }
}

