package com.bank.Bank.Management.System.service.Impl;

import com.bank.Bank.Management.System.dto.request.CustomerRequest;
import com.bank.Bank.Management.System.dto.response.CustomerResponse;
import com.bank.Bank.Management.System.entity.BankAccount;
import com.bank.Bank.Management.System.entity.Customer;
import com.bank.Bank.Management.System.enums.Role;
import com.bank.Bank.Management.System.exception.AlreadyExistsException;
import com.bank.Bank.Management.System.exception.NotFoundException;
import com.bank.Bank.Management.System.mapper.CustomerMapper;
import com.bank.Bank.Management.System.repository.BankRepository;
import com.bank.Bank.Management.System.repository.CustomerRepository;
import com.bank.Bank.Management.System.service.CustomerService;
import com.bank.Bank.Management.System.utility.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BankRepository bankRepository;

    @Override
    public CustomerResponse createAccount(CustomerRequest customerRequest) {

        if (customerRepository.existsByPin(customerRequest.getPin())) {
            throw new AlreadyExistsException("This PIN already. Please enter more pin.");
        }

        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new AlreadyExistsException("This Email already. Please enter more email.");
        }

        Customer newCustomer = Customer.builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .gender(customerRequest.getGender())
                .address(customerRequest.getAddress())
                .email(customerRequest.getEmail())
                .phone(customerRequest.getPhone())
                .pin(customerRequest.getPin())
                .status("ACTIVE")
                .role(customerRequest.getRole())
                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);

        BankAccount bankAccount = BankAccount.builder()
                .customer(savedCustomer)
                .accountNumber(AccountUtils.generateAccountNumber())
                .balance(BigDecimal.ZERO)
                .build();

        bankRepository.save(bankAccount);

        return customerMapper.toDto(savedCustomer);
    }

    @Override
    public CustomerResponse getUserByPin(Integer pin) {
        return Optional.ofNullable(customerRepository.findByPin(pin))
                .map(customerMapper::toDto)
                .orElseThrow(() -> new NotFoundException("This PIN not found"));
    }

    @Override
    public CustomerResponse getByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email))
                .map(customerMapper::toDto)
                .orElseThrow(() -> new NotFoundException("This email not found"));
    }

    @Override
    public CustomerResponse getByPhone(String phone) {
        return Optional.ofNullable(customerRepository.findByPhone(phone))
                .map(customerMapper::toDto)
                .orElseThrow(() -> new NotFoundException("This phone number not found"));
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));
        return customerMapper.toDto(customer);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void update(Long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));
        customerMapper.updateFromDto(customerRequest, customer);
        customerRepository.save(customer);
    }

}
