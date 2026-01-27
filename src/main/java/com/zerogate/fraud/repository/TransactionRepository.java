package com.zerogate.fraud.repository;

import com.zerogate.fraud.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    @Query("SELECT t FROM Transaction t WHERE t.accountId = :accountId AND t.timestamp >= :since")
    List<Transaction> findRecentTransactions(@Param("accountId") String accountId, @Param("since") LocalDateTime since);
}
