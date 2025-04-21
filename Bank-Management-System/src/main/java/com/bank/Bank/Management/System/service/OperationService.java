package com.bank.Bank.Management.System.service;

import com.bank.Bank.Management.System.dto.response.OperationResponse;

import java.util.List;

public interface OperationService {

    List<OperationResponse> getAllOperationsByAccount(String accountNumber);

}
