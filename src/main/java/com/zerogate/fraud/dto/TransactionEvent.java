package com.zerogate.fraud.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionEvent {
    private String transactionId;
    private String accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransactionEvent() {}

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
