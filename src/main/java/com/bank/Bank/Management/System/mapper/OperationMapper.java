package com.bank.Bank.Management.System.mapper;

import com.bank.Bank.Management.System.dto.response.OperationResponse;
import com.bank.Bank.Management.System.entity.Operation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    OperationResponse toDto(Operation operation);
}
