package com.bank.Bank.Management.System.mapper;

import com.bank.Bank.Management.System.dto.request.CustomerRequest;
import com.bank.Bank.Management.System.dto.response.CustomerResponse;
import com.bank.Bank.Management.System.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface CustomerMapper {

    //Customer toEntity(CustomerRequest customerRequest);

    CustomerResponse toDto(Customer customer);

    void updateFromDto(CustomerRequest customerRequest, @MappingTarget Customer customer);
}
