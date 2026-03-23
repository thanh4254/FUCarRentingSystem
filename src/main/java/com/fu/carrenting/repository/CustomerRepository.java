package com.fu.carrenting.repository;

import com.fu.carrenting.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByIdentityCard(String identityCard);
    boolean existsByLicenceNumber(String licenceNumber);

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.customerName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "c.mobile LIKE CONCAT('%', :keyword, '%')")
    List<Customer> searchCustomers(String keyword);

    Optional<Customer> findByAccount_AccountId(Integer accountId);
}
