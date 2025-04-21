package com.bank.Bank.Management.System.dto.request;

import com.bank.Bank.Management.System.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private String name;

    private String surname;

    private String gender;

    private String address;

    private String email;

    private String phone;

    private Integer pin;

    private Role role;

}
