package com.bank.Bank.Management.System.mapper;

import com.bank.Bank.Management.System.dto.response.BankResponse;
import com.bank.Bank.Management.System.entity.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface BankMapper {


    BankResponse toDto(BankAccount bankAccount);

}
