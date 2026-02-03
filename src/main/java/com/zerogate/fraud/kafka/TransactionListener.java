package com.zerogate.fraud.kafka;

import com.zerogate.fraud.dto.TransactionEvent;
import com.zerogate.fraud.service.FraudService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final FraudService fraudService;

    public TransactionListener(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @KafkaListener(topics = "transactions", groupId = "fraud-group")
    public void listen(TransactionEvent event) {
        fraudService.processTransaction(event);
    }
}
