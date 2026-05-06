package com.buildledger.compliance.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AuditRequestDTO {
    @NotNull(message = "Compliance officer ID is required") @Positive private Long complianceOfficerId;
    @NotBlank(message = "Scope is required") @Size(min=5, max=300) private String scope;
    @Size(max = 2000) private String findings;
    @NotNull(message = "Scheduled date is required") @FutureOrPresent private LocalDate date;
}

