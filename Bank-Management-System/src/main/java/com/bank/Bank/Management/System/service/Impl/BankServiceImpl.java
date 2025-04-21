package com.bank.Bank.Management.System.service.Impl;

import com.bank.Bank.Management.System.dto.response.BankResponse;
import com.bank.Bank.Management.System.entity.BankAccount;
import com.bank.Bank.Management.System.entity.Customer;
import com.bank.Bank.Management.System.entity.Operation;
import com.bank.Bank.Management.System.exception.NotFoundException;
import com.bank.Bank.Management.System.mapper.BankMapper;
import com.bank.Bank.Management.System.repository.BankRepository;
import com.bank.Bank.Management.System.repository.CustomerRepository;
import com.bank.Bank.Management.System.repository.OperationRepository;
import com.bank.Bank.Management.System.service.BankService;
import com.bank.Bank.Management.System.utility.AccountUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final BankMapper bankMapper;
    private final OperationRepository operationRepository;


    @Override
    public BankResponse addBankAccountById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));

        BankAccount newAccount = BankAccount.builder()
                .accountNumber(AccountUtils.generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();

        BankAccount savedAccount = bankRepository.save(newAccount);

        return bankMapper.toDto(savedAccount);
    }

    @Override
    public BankResponse addBankAccountByPin(Integer pin) {
        Customer customer = customerRepository.findByPin(pin);
        if (customer == null) {
            throw new NotFoundException("Customer with this PIN not found");
        }

        BankAccount newAccount = BankAccount.builder()
                .accountNumber(AccountUtils.generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .build();

        BankAccount savedAccount = bankRepository.save(newAccount);

        return bankMapper.toDto(savedAccount);
    }

    @Override
    public List<BankResponse> getByAllBankAccount() {
        return bankRepository.findAll().stream().map(bankMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BankResponse> getBankAccountsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + customerId));

        List<BankAccount> accounts = customer.getAccounts();

        return accounts.stream()
                .map(bankMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankResponse getByAccountNumber(String accountNumber) {

        if (!bankRepository.existsByAccountNumber(accountNumber)) {
            throw new NotFoundException("Account Number not found");
        }

        BankAccount findAccount = bankRepository.findByAccountNumber(accountNumber);

        return bankMapper.toDto(findAccount);
    }

    @Override
    public List<BankResponse> getBankAccountsByCustomerPin(Integer pin) {
        Customer customer = customerRepository.findByPin(pin);
        if (customer == null) {
            throw new NotFoundException("Customer with PIN not found");
        }

        List<BankAccount> accounts = customer.getAccounts();

        return accounts.stream().map(bankMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BankResponse withdraw(String accountNumber, BigDecimal amount) {
        BankAccount account = bankRepository.findByAccountNumber(accountNumber);
        if (account == null) throw new NotFoundException("Account not found");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (account.getBalance().compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient balance");

        account.setBalance(account.getBalance().subtract(amount));
        bankRepository.save(account);

        Operation operation = Operation.builder()
                .type("WITHDRAW")
                .fromAccount(accountNumber)
                .toAccount("Terminal")
                .amount(amount)
                .date(LocalDateTime.now())
                .bankAccount(account)
                .build();

        operationRepository.save(operation);
        return bankMapper.toDto(account);
    }

    @Transactional
    @Override
    public BankResponse deposit(String accountNumber, BigDecimal amount) {
        BankAccount account = bankRepository.findByAccountNumber(accountNumber);
        if (account == null) throw new NotFoundException("Account not found");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");

        account.setBalance(account.getBalance().add(amount));
        bankRepository.save(account);

        Operation operation = Operation.builder()
                .type("DEPOSIT")
                .fromAccount("Terminal")
                .toAccount(accountNumber)
                .amount(amount)
                .date(LocalDateTime.now())
                .bankAccount(account)
                .build();

        operationRepository.save(operation);
        return bankMapper.toDto(account);
    }

    @Transactional
    @Override
    public String transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if (fromAccountNumber.equals(toAccountNumber))
            throw new IllegalArgumentException("Cannot transfer to the same account");

        BankAccount fromAccount = bankRepository.findByAccountNumber(fromAccountNumber);
        BankAccount toAccount = bankRepository.findByAccountNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null)
            throw new NotFoundException("One or both accounts not found");

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        if (fromAccount.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        bankRepository.save(fromAccount);
        bankRepository.save(toAccount);

        LocalDateTime now = LocalDateTime.now();

        Operation withdrawOp = Operation.builder()
                .type("TRANSFER_OUT")
                .fromAccount(fromAccountNumber)
                .toAccount(toAccountNumber)
                .amount(amount)
                .date(now)
                .bankAccount(fromAccount)
                .build();

        Operation depositOp = Operation.builder()
                .type("TRANSFER_IN")
                .fromAccount(fromAccountNumber)
                .toAccount(toAccountNumber)
                .amount(amount)
                .date(now)
                .bankAccount(toAccount)
                .build();

        operationRepository.save(withdrawOp);
        operationRepository.save(depositOp);
        return fromAccountNumber;
    }

}
