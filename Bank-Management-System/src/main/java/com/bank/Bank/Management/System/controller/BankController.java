package com.bank.Bank.Management.System.controller;

import com.bank.Bank.Management.System.dto.request.BankRequest;
import com.bank.Bank.Management.System.dto.response.BankResponse;
import com.bank.Bank.Management.System.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    @GetMapping("/getAllBankAccount")
    public List<BankResponse> getByAllBankAccount() {
        return bankService.getByAllBankAccount();
    }

    @PostMapping("/add-account-by-id/{id}")
    public ResponseEntity<BankResponse> addBankAccountById(@PathVariable Long id) {
        BankResponse newAccount = bankService.addBankAccountById(id);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/add-account-by-pin/{pin}")
    public ResponseEntity<BankResponse> addBankAccountByPin(@PathVariable Integer pin) {
        BankResponse newAccount = bankService.addBankAccountByPin(pin);
        return ResponseEntity.ok(newAccount);
    }
    @GetMapping("/getByAccountNumber")
    public BankResponse getByAccountNumber(@RequestParam String accountNumber){
        return bankService.getByAccountNumber(accountNumber);
    }

    @GetMapping("/by-customer-id/{id}")
    public ResponseEntity<List<BankResponse>> getAccountsByCustomerId(@PathVariable Long id) {
        List<BankResponse> accounts = bankService.getBankAccountsByCustomerId(id);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/by-customer-pin/{pin}")
    public ResponseEntity<List<BankResponse>> getAccountsByCustomerPin(@PathVariable Integer pin) {
        List<BankResponse> accounts = bankService.getBankAccountsByCustomerPin(pin);
        return ResponseEntity.ok(accounts);
    }
    @PostMapping("/deposit")
    public BankResponse deposit(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount
    ) {
        return bankService.deposit(accountNumber, amount);
    }

    @PostMapping("/withdraw")
    public BankResponse withdraw(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount
    ) {
        return bankService.withdraw(accountNumber, amount);
    }

    @PostMapping("/transfer")
    public String transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam BigDecimal amount
    ) {
        return bankService.transfer(fromAccount, toAccount, amount);
    }

}
