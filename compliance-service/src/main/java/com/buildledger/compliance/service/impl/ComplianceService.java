package com.buildledger.compliance.service.impl;

import com.buildledger.compliance.dto.request.AuditRequestDTO;
import com.buildledger.compliance.dto.request.ComplianceRecordRequestDTO;
import com.buildledger.compliance.dto.response.*;
import com.buildledger.compliance.entity.Audit;
import com.buildledger.compliance.entity.ComplianceRecord;
import com.buildledger.compliance.enums.AuditStatus;
import com.buildledger.compliance.enums.ComplianceStatus;
import com.buildledger.compliance.feign.ContractServiceClient;
import com.buildledger.compliance.feign.IamServiceClient;
import com.buildledger.compliance.repository.AuditRepository;
import com.buildledger.compliance.repository.ComplianceRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j @Transactional
public class ComplianceService {

    private final ComplianceRecordRepository complianceRecordRepository;
    private final AuditRepository auditRepository;
    private final ContractServiceClient contractServiceClient;
    private final IamServiceClient iamServiceClient;

    // ── Compliance Records ────────────────────────────────────────────────────

    public ComplianceRecordResponseDTO createComplianceRecord(ComplianceRecordRequestDTO request,
                                                               String reviewerUsername) {
        log.info("Creating compliance record for contract {}", request.getContractId());
        validateContract(request.getContractId());

        ComplianceRecord record = ComplianceRecord.builder()
            .contractId(request.getContractId()).type(request.getType()).result(request.getResult())
            .date(request.getDate()).notes(request.getNotes()).status(ComplianceStatus.PENDING)
            .reviewedBy(reviewerUsername).build();

        return mapComplianceToResponse(complianceRecordRepository.save(record));
    }

    @Transactional(readOnly = true)
    public ComplianceRecordResponseDTO getComplianceRecordById(Long id) {
        return mapComplianceToResponse(findComplianceById(id));
    }

    @Transactional(readOnly = true)
    public List<ComplianceRecordResponseDTO> getAllComplianceRecords() {
        return complianceRecordRepository.findAll().stream().map(this::mapComplianceToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ComplianceRecordResponseDTO> getByContract(Long contractId) {
        validateContract(contractId);
        return complianceRecordRepository.findByContractId(contractId).stream()
            .map(this::mapComplianceToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ComplianceRecordResponseDTO> getByStatus(ComplianceStatus status) {
        return complianceRecordRepository.findByStatus(status).stream()
            .map(this::mapComplianceToResponse).collect(Collectors.toList());
    }

    public ComplianceRecordResponseDTO updateComplianceStatus(Long id, ComplianceStatus newStatus,
                                                               String reviewerUsername) {
        ComplianceRecord record = findComplianceById(id);
        ComplianceStatus current = record.getStatus();

        if (!current.canTransitionTo(newStatus)) {
            throw new RuntimeException(
                "Invalid compliance status transition from " + current + " to " + newStatus +
                ". Lifecycle: PENDING→UNDER_REVIEW, UNDER_REVIEW→PASSED|FAILED|WAIVED, FAILED→PENDING.");
        }

        record.setStatus(newStatus);
        record.setReviewedBy(reviewerUsername);
        return mapComplianceToResponse(complianceRecordRepository.save(record));
    }

    // ── Audits ────────────────────────────────────────────────────────────────

    public AuditResponseDTO createAudit(AuditRequestDTO request) {
        log.info("Creating audit for officer {}", request.getComplianceOfficerId());
        Map<String, Object> user = validateOfficer(request.getComplianceOfficerId());

        Audit audit = Audit.builder()
            .complianceOfficerId(request.getComplianceOfficerId())
            .officerName((String) user.get("name"))
            .scope(request.getScope()).findings(request.getFindings())
            .date(request.getDate()).status(AuditStatus.SCHEDULED).build();

        return mapAuditToResponse(auditRepository.save(audit));
    }

    @Transactional(readOnly = true)
    public AuditResponseDTO getAuditById(Long auditId) {
        return mapAuditToResponse(findAuditById(auditId));
    }

    @Transactional(readOnly = true)
    public List<AuditResponseDTO> getAllAudits() {
        return auditRepository.findAll().stream().map(this::mapAuditToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuditResponseDTO> getAuditsByOfficer(Long officerId) {
        return auditRepository.findByComplianceOfficerId(officerId).stream()
            .map(this::mapAuditToResponse).collect(Collectors.toList());
    }

    public AuditResponseDTO updateAuditStatus(Long auditId, AuditStatus newStatus, String findings) {
        Audit audit = findAuditById(auditId);
        AuditStatus current = audit.getStatus();

        if (!current.canTransitionTo(newStatus)) {
            throw new RuntimeException(
                "Invalid audit status transition from " + current + " to " + newStatus +
                ". Lifecycle: SCHEDULED→IN_PROGRESS|CANCELLED, IN_PROGRESS→PENDING_REVIEW|CANCELLED, PENDING_REVIEW→COMPLETED|CANCELLED.");
        }

        audit.setStatus(newStatus);
        if (newStatus == AuditStatus.IN_PROGRESS && audit.getAuditDate() == null) {
            audit.setAuditDate(LocalDate.now());
        }
        if (findings != null) audit.setFindings(findings);
        return mapAuditToResponse(auditRepository.save(audit));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void validateContract(Long contractId) {
        ApiResponseDTO<Map<String, Object>> res;
        try { res = contractServiceClient.getContractById(contractId); }
        catch (Exception e) { throw new RuntimeException("Contract Service unavailable. Cannot validate Contract ID " + contractId + "."); }
        if (!res.isSuccess() || res.getData() == null)
            throw new RuntimeException("Contract ID " + contractId + " does not exist");
    }

    private Map<String, Object> validateOfficer(Long officerId) {
        ApiResponseDTO<Map<String, Object>> res;
        try { res = iamServiceClient.getUserById(officerId); }
        catch (Exception e) { throw new RuntimeException("IAM Service unavailable. Cannot validate officer ID " + officerId + "."); }
        if (!res.isSuccess() || res.getData() == null)
            throw new RuntimeException("Compliance Officer with ID " + officerId + " does not exist");
        String role = (String) res.getData().get("role");
        if (!"COMPLIANCE_OFFICER".equals(role) && !"ADMIN".equals(role))
            throw new RuntimeException("User ID " + officerId + " is not a COMPLIANCE_OFFICER. Role: " + role);
        return res.getData();
    }

    private ComplianceRecord findComplianceById(Long id) {
        return complianceRecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ComplianceRecord not found with id: " + id));
    }

    private Audit findAuditById(Long id) {
        return auditRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Audit not found with id: " + id));
    }

    private ComplianceRecordResponseDTO mapComplianceToResponse(ComplianceRecord r) {
        return ComplianceRecordResponseDTO.builder()
            .complianceId(r.getComplianceId()).contractId(r.getContractId()).type(r.getType())
            .result(r.getResult()).date(r.getDate()).notes(r.getNotes()).status(r.getStatus())
            .reviewedBy(r.getReviewedBy()).createdAt(r.getCreatedAt()).build();
    }

    private AuditResponseDTO mapAuditToResponse(Audit a) {
        return AuditResponseDTO.builder()
            .auditId(a.getAuditId()).complianceOfficerId(a.getComplianceOfficerId())
            .officerName(a.getOfficerName()).scope(a.getScope()).findings(a.getFindings())
            .date(a.getDate()).auditDate(a.getAuditDate()).status(a.getStatus())
            .createdAt(a.getCreatedAt()).updatedAt(a.getUpdatedAt()).build();
    }
}

