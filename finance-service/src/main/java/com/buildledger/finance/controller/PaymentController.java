package com.buildledger.finance.controller;

import com.buildledger.finance.dto.request.PaymentRequestDTO;
import com.buildledger.finance.dto.response.ApiResponseDTO;
import com.buildledger.finance.dto.response.PaymentResponseDTO;
import com.buildledger.finance.enums.PaymentStatus;
import com.buildledger.finance.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/payments")
@RequiredArgsConstructor @Tag(name = "Payment Management") @SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final FinanceService financeService;

    @PostMapping
    @PreAuthorize("hasRole('FINANCE_OFFICER') or hasRole('ADMIN')")
    @Operation(summary = "Process payment for an APPROVED invoice [FINANCE_OFFICER / ADMIN]")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> processPayment(@Valid @RequestBody PaymentRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponseDTO.success("Payment initiated", financeService.processPayment(req)));
    }

    @GetMapping
    @Operation(summary = "Get all payments [ALL roles]")
    public ResponseEntity<ApiResponseDTO<List<PaymentResponseDTO>>> getAllPayments() {
        return ResponseEntity.ok(ApiResponseDTO.success("Payments retrieved", financeService.getAllPayments()));
    }

    @GetMapping("/{paymentId}")
    @Operation(summary = "Get payment by ID [ALL roles]")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(ApiResponseDTO.success("Payment retrieved", financeService.getPaymentById(paymentId)));
    }

    @GetMapping("/invoice/{invoiceId}")
    @Operation(summary = "Get payments by invoice [ALL roles]")
    public ResponseEntity<ApiResponseDTO<List<PaymentResponseDTO>>> getByInvoice(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(ApiResponseDTO.success("Payments retrieved", financeService.getPaymentsByInvoice(invoiceId)));
    }

    @PatchMapping("/{paymentId}/status")
    @PreAuthorize("hasRole('FINANCE_OFFICER') or hasRole('ADMIN')")
    @Operation(summary = "Update payment status [FINANCE_OFFICER / ADMIN]",
               description = "Lifecycle: PENDING→PROCESSING|FAILED, PROCESSING→COMPLETED|FAILED")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> updateStatus(
            @PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(ApiResponseDTO.success("Payment status updated",
            financeService.updatePaymentStatus(paymentId, status)));
    }
}

