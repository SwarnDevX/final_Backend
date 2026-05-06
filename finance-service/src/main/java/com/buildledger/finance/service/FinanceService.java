package com.buildledger.finance.service;

import com.buildledger.finance.dto.request.InvoiceRequestDTO;
import com.buildledger.finance.dto.request.PaymentRequestDTO;
import com.buildledger.finance.dto.response.InvoiceResponseDTO;
import com.buildledger.finance.dto.response.PaymentResponseDTO;
import com.buildledger.finance.enums.InvoiceStatus;
import com.buildledger.finance.enums.PaymentStatus;
import java.util.List;

public interface FinanceService {
    // Invoice operations
    InvoiceResponseDTO submitInvoice(InvoiceRequestDTO request);
    InvoiceResponseDTO getInvoiceById(Long invoiceId);
    List<InvoiceResponseDTO> getAllInvoices();
    List<InvoiceResponseDTO> getInvoicesByContract(Long contractId);
    List<InvoiceResponseDTO> getInvoicesByStatus(InvoiceStatus status);
    InvoiceResponseDTO approveInvoice(Long invoiceId);
    InvoiceResponseDTO rejectInvoice(Long invoiceId, String reason);
    void deleteInvoice(Long invoiceId);
    
    // Payment operations
    PaymentResponseDTO processPayment(PaymentRequestDTO request);
    PaymentResponseDTO getPaymentById(Long paymentId);
    List<PaymentResponseDTO> getAllPayments();
    List<PaymentResponseDTO> getPaymentsByInvoice(Long invoiceId);
    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatus newStatus);
}

