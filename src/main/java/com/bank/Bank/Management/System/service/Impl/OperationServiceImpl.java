package com.bank.Bank.Management.System.service.Impl;

import com.bank.Bank.Management.System.dto.response.OperationResponse;
import com.bank.Bank.Management.System.entity.BankAccount;
import com.bank.Bank.Management.System.entity.Operation;
import com.bank.Bank.Management.System.exception.NotFoundException;
import com.bank.Bank.Management.System.mapper.OperationMapper;
import com.bank.Bank.Management.System.repository.BankRepository;
import com.bank.Bank.Management.System.repository.OperationRepository;
import com.bank.Bank.Management.System.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final BankRepository bankRepository;

    @Override
    public List<OperationResponse> getAllOperationsByAccount(String accountNumber) {
        BankAccount account = bankRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }

        List<Operation> operations = operationRepository.findAll()
                .stream()
                .filter(op -> accountNumber.equals(op.getFromAccount()) || accountNumber.equals(op.getToAccount()))
                .collect(Collectors.toList());

        return operations.stream()
                .map(operationMapper::toDto)
                .collect(Collectors.toList());
    }
}
