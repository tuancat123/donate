package com.example.Donate.Repository;

import com.example.Donate.Entity.BankAccount;
import com.example.Donate.Entity.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    List<BankAccount> findByOrganization(Organizations organizations);
}
