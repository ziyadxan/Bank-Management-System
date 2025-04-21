package com.bank.Bank.Management.System.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponse {

    private Long id;

    private CustomerResponse customer;

    private String accountNumber;

    private BigDecimal balance;

    private Date regDate;

    private LocalDateTime lastModifiedAt;
}
