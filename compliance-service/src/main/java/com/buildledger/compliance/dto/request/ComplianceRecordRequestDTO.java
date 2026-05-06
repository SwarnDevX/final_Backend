package com.buildledger.compliance.dto.request;

import com.buildledger.compliance.enums.ComplianceType;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ComplianceRecordRequestDTO {
    @NotNull(message = "Contract ID is required")
    @Positive
    private Long contractId;
    @NotNull(message = "Compliance type is required")
    private ComplianceType type;
    @Size(max = 200, message = "Result cannot exceed 200 characters")
    private String result;
    @NotNull(message = "Date is required")
    @PastOrPresent
    private LocalDate date;
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;
}

