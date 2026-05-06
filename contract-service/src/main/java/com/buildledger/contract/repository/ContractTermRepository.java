package com.buildledger.contract.repository;

import com.buildledger.contract.entity.ContractTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractTermRepository extends JpaRepository<ContractTerm, Long> {
    List<ContractTerm> findByContractContractIdOrderBySequenceNumberAsc(Long contractId);
}

