package com.example.Donate.Service;

import com.example.Donate.Entity.Donations;
import com.example.Donate.Entity.Transactions;
import com.example.Donate.Repository.TransactionRepository;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionsRepository;

    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public Transactions saveTransaction(Transactions transaction) {
        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(LocalDateTime.now());
        }
        if (transaction.getStatus() == null) {
            transaction.setStatus(Transactions.Status.SUCCESS);
        }
        return transactionsRepository.save(transaction);
    }

    public Transactions getTransactionById(Integer id) {
        return transactionsRepository.findById(id).orElse(null);
    }
}

