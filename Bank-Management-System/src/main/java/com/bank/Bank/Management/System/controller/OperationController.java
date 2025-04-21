package com.bank.Bank.Management.System.controller;

import com.bank.Bank.Management.System.dto.response.OperationResponse;
import com.bank.Bank.Management.System.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping("/{accountNumber}")
    public List<OperationResponse> getOperations(@PathVariable String accountNumber) {
        return operationService.getAllOperationsByAccount(accountNumber);
    }
}