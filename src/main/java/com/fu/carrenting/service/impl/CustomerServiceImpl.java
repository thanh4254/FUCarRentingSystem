package com.fu.carrenting.service.impl;

import com.fu.carrenting.entity.Customer;
import com.fu.carrenting.repository.CustomerRepository;
import com.fu.carrenting.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override public List<Customer> findAll() { return customerRepository.findAll(); }
    @Override public Optional<Customer> findById(Integer id) { return customerRepository.findById(id); }
    @Override public Optional<Customer> findByEmail(String email) { return customerRepository.findByEmail(email); }
    @Override public Customer save(Customer customer) { return customerRepository.save(customer); }
    @Override public void deleteById(Integer id) { customerRepository.deleteById(id); }
    @Override public boolean existsByEmail(String email) { return customerRepository.existsByEmail(email); }
    @Override public Optional<Customer> findByAccountId(Integer accountId) { return customerRepository.findByAccount_AccountId(accountId); }

    @Override
    public List<Customer> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        return customerRepository.searchCustomers(keyword);
    }
}