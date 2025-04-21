package com.bank.Bank.Management.System.service;

import com.bank.Bank.Management.System.dto.response.BankResponse;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {

    BankResponse addBankAccountById(Long id);

    BankResponse addBankAccountByPin(Integer pin);

    List<BankResponse> getByAllBankAccount();

    List<BankResponse> getBankAccountsByCustomerId(Long customerId);

    BankResponse getByAccountNumber(String accountNumber);

    List<BankResponse> getBankAccountsByCustomerPin(Integer pin);

    BankResponse withdraw(String accountNumber, BigDecimal amount);

    BankResponse deposit(String accountNumber, BigDecimal amount);

    String transfer(String fromAccount, String toAccount, BigDecimal amount);

}
