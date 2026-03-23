package com.fu.carrenting.service;

import com.fu.carrenting.entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();
    Optional<Customer> findById(Integer id);
    Optional<Customer> findByEmail(String email);
    Customer save(Customer customer);
    void deleteById(Integer id);
    List<Customer> search(String keyword);
    boolean existsByEmail(String email);
    Optional<Customer> findByAccountId(Integer accountId);
}