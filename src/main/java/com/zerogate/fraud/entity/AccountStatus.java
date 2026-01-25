package com.zerogate.fraud.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_status")
public class AccountStatus {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(nullable = false)
    private boolean isFraudulent;

    @Column(name = "flagged_at")
    private LocalDateTime flaggedAt;

    public AccountStatus() {}

    public AccountStatus(String accountId, boolean isFraudulent, LocalDateTime flaggedAt) {
        this.accountId = accountId;
        this.isFraudulent = isFraudulent;
        this.flaggedAt = flaggedAt;
    }

    public String getAccountId() { return accountId; }
    public boolean isFraudulent() { return isFraudulent; }
    public LocalDateTime getFlaggedAt() { return flaggedAt; }

    public void setFraudulent(boolean fraudulent) { isFraudulent = fraudulent; }
    public void setFlaggedAt(LocalDateTime flaggedAt) { this.flaggedAt = flaggedAt; }
}
