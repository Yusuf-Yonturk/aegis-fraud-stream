package com.zerogate.fraud.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String status;

    public Transaction() {}

    public Transaction(String transactionId, String accountId, BigDecimal amount, LocalDateTime timestamp, String status) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}
