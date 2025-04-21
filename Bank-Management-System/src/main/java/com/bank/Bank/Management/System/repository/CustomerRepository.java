package com.bank.Bank.Management.System.repository;

import com.bank.Bank.Management.System.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByPin(Integer pin);

    Customer findByPin(Integer pin);

    boolean existsByEmail(String email);

    Customer findByEmail(String email);

    Customer findByPhone(String phone);

}
