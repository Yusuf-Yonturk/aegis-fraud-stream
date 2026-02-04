package com.zerogate.fraud.service;

import com.zerogate.fraud.dto.TransactionEvent;
import com.zerogate.fraud.entity.AccountStatus;
import com.zerogate.fraud.repository.AccountStatusRepository;
import com.zerogate.fraud.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FraudServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountStatusRepository accountStatusRepository;

    @InjectMocks
    private FraudService fraudService;

    @Test
    void shouldRejectWhenAccountAlreadyFraudulent() {
        TransactionEvent event = new TransactionEvent();
        event.setAccountId("ACC-123");
        event.setAmount(new BigDecimal("100.00"));
        event.setTimestamp(LocalDateTime.now());
        event.setTransactionId("TX-001");

        AccountStatus status = new AccountStatus("ACC-123", true, LocalDateTime.now());
        when(accountStatusRepository.findById("ACC-123")).thenReturn(Optional.of(status));

        fraudService.processTransaction(event);

        verify(transactionRepository).save(argThat(tx -> tx.getStatus().equals("REJECTED_FRAUD_ACCOUNT")));
    }
}
