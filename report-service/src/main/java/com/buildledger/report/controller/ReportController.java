package com.buildledger.report.controller;

import com.buildledger.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Report Generation")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/projects")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @Operation(summary = "Generate project report [ADMIN / PROJECT_MANAGER]")
    public ResponseEntity<Map<String, Object>> getProjectReport() {
        return ResponseEntity.ok(reportService.generateProjectReport());
    }

    @GetMapping("/contracts")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @Operation(summary = "Generate contract report [ADMIN / PROJECT_MANAGER]")
    public ResponseEntity<Map<String, Object>> getContractReport() {
        return ResponseEntity.ok(reportService.generateContractReport());
    }

    @GetMapping("/financial")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FINANCE_OFFICER')")
    @Operation(summary = "Generate financial report [ADMIN / FINANCE_OFFICER]")
    public ResponseEntity<Map<String, Object>> getFinancialReport() {
        return ResponseEntity.ok(reportService.generateFinancialReport());
    }

    @GetMapping("/deliveries")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @Operation(summary = "Generate delivery report [ADMIN / PROJECT_MANAGER]")
    public ResponseEntity<Map<String, Object>> getDeliveryReport() {
        return ResponseEntity.ok(reportService.generateDeliveryReport());
    }
}

