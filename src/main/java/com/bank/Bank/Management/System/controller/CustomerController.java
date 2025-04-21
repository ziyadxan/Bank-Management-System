package com.bank.Bank.Management.System.controller;

import com.bank.Bank.Management.System.dto.request.CustomerRequest;
import com.bank.Bank.Management.System.dto.response.CustomerResponse;
import com.bank.Bank.Management.System.service.BankService;
import com.bank.Bank.Management.System.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final BankService bankService;

    @PostMapping()
    public CustomerResponse createAccount(@RequestBody CustomerRequest customerRequest){
        return customerService.createAccount(customerRequest);
    }

    @GetMapping("/getAllCustomer")
    public List<CustomerResponse> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/getById/{id}")
    public CustomerResponse getById(@PathVariable Long id){
        return customerService.getById(id);
    }

    @GetMapping("/getByPin")
    public CustomerResponse getCustomerByPin(@RequestParam Integer pin){
        return customerService.getUserByPin(pin);
    }

    @GetMapping("/getByEmail/")
    public CustomerResponse getCustomerByEmail(@RequestParam String email){
        return customerService.getByEmail(email);
    }

    @GetMapping("/getByPhone")
    public CustomerResponse getCustomerByPhone(@RequestParam String phone){
        return customerService.getByPhone(phone);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody CustomerRequest accountRequest){
        customerService.update(id,accountRequest);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        customerService.delete(id);
    }

}
