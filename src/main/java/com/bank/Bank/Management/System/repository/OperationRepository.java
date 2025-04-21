package com.bank.Bank.Management.System.repository;

import com.bank.Bank.Management.System.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}

