package com.buildledger.compliance.dto.response;

import com.buildledger.compliance.enums.AuditStatus;
import lombok.*; import java.time.LocalDate; import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuditResponseDTO {
    private Long auditId; private Long complianceOfficerId; private String officerName;
    private String scope; private String findings; private LocalDate date; private LocalDate auditDate;
    private AuditStatus status; private LocalDateTime createdAt; private LocalDateTime updatedAt;
}

