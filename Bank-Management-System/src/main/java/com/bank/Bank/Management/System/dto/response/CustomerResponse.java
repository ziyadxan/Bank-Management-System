package com.bank.Bank.Management.System.dto.response;

import com.bank.Bank.Management.System.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long id;

    private String name;

    private String surname;

    private String gender;

    private String address;

    private String email;

    private String phone;

    private Integer pin;

    private String status;

    private Role role;

    private Date regDate;

    private LocalDateTime lastModifiedAt;
}
