package com.buildledger.vendor.repository;

import com.buildledger.vendor.entity.VendorDocument;
import com.buildledger.vendor.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorDocumentRepository extends JpaRepository<VendorDocument, Long> {
    Optional<VendorDocument> findByVendorVendorId(Long vendorId);
    List<VendorDocument> findAllByVendorVendorId(Long vendorId);
    List<VendorDocument> findByVerificationStatus(VerificationStatus status);
}

