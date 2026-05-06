package com.buildledger.compliance.repository;

import com.buildledger.compliance.entity.Audit;
import com.buildledger.compliance.enums.AuditStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByComplianceOfficerId(Long officerId);
    List<Audit> findByStatus(AuditStatus status);
}

