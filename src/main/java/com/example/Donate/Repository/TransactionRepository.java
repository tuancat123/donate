package com.example.Donate.Repository;

import com.example.Donate.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {
    // Có thể thêm các truy vấn tùy chỉnh nếu cần, ví dụ:
    Transactions findByTransactionCode(String transactionCode);
}
