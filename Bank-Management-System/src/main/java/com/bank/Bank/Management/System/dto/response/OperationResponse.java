package com.bank.Bank.Management.System.dto.response;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationResponse {

    private Long id;
    private String type;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private LocalDateTime date;
}
