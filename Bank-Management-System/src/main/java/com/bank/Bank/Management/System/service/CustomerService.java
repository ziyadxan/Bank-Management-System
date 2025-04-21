package com.bank.Bank.Management.System.service;


import com.bank.Bank.Management.System.dto.request.CustomerRequest;
import com.bank.Bank.Management.System.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse createAccount(CustomerRequest customerRequest);

    CustomerResponse getUserByPin(Integer pin);

    CustomerResponse getByEmail(String email);

    CustomerResponse getByPhone(String phone);

    List<CustomerResponse> getAllCustomer();

    CustomerResponse getById(Long id);

    void delete(Long id);

    void update(Long id, CustomerRequest customerRequest);

}
