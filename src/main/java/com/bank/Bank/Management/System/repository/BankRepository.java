package com.bank.Bank.Management.System.repository;

import com.bank.Bank.Management.System.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByAccountNumber(String accountNumber);

    BankAccount findByAccountNumber(String accountNumber);

}
