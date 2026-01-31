package com.zerogate.fraud.service;

import com.zerogate.fraud.dto.TransactionEvent;
import com.zerogate.fraud.entity.AccountStatus;
import com.zerogate.fraud.entity.Transaction;
import com.zerogate.fraud.repository.AccountStatusRepository;
import com.zerogate.fraud.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FraudService {

    private final TransactionRepository transactionRepository;
    private final AccountStatusRepository accountStatusRepository;

    private static final int MAX_TRANSACTIONS_PER_MINUTE = 3;
    private static final BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("10000.00");

    public FraudService(TransactionRepository transactionRepository, AccountStatusRepository accountStatusRepository) {
        this.transactionRepository = transactionRepository;
        this.accountStatusRepository = accountStatusRepository;
    }

    @Transactional
    public void processTransaction(TransactionEvent event) {
        Optional<AccountStatus> statusOpt = accountStatusRepository.findById(event.getAccountId());
        if (statusOpt.isPresent() && statusOpt.get().isFraudulent()) {
            saveTransaction(event, "REJECTED_FRAUD_ACCOUNT");
            return;
        }

        if (event.getAmount().compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            flagAccount(event.getAccountId());
            saveTransaction(event, "REJECTED_HIGH_AMOUNT");
            return;
        }

        LocalDateTime oneMinuteAgo = event.getTimestamp().minusMinutes(1);
        List<Transaction> recentTx = transactionRepository.findRecentTransactions(event.getAccountId(), oneMinuteAgo);

        if (recentTx.size() >= MAX_TRANSACTIONS_PER_MINUTE) {
            flagAccount(event.getAccountId());
            saveTransaction(event, "REJECTED_HIGH_FREQUENCY");
            return;
        }

        saveTransaction(event, "APPROVED");
    }

    private void flagAccount(String accountId) {
        AccountStatus status = new AccountStatus(accountId, true, LocalDateTime.now());
        accountStatusRepository.save(status);
    }

    private void saveTransaction(TransactionEvent event, String status) {
        Transaction tx = new Transaction(
                event.getTransactionId(),
                event.getAccountId(),
                event.getAmount(),
                event.getTimestamp(),
                status
        );
        transactionRepository.save(tx);
    }
}
