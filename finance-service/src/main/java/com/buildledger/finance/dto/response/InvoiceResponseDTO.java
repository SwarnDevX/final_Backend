package com.buildledger.finance.dto.response;

import com.buildledger.finance.enums.InvoiceStatus;
import lombok.*; import java.math.BigDecimal; import java.time.LocalDate; import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InvoiceResponseDTO {
    private Long invoiceId; private Long contractId; private String vendorName;
    private BigDecimal amount; private LocalDate date; private LocalDate dueDate;
    private String description; private InvoiceStatus status; private String rejectionReason;
    private LocalDateTime createdAt; private LocalDateTime updatedAt;
}

