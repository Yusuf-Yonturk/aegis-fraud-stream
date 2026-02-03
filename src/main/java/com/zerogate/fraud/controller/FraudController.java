package com.zerogate.fraud.controller;

import com.zerogate.fraud.dto.TransactionEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/test")
public class FraudController {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public FraudController(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/publish")
    public String publishMockTransaction(@RequestBody TransactionEvent event) {
        if (event.getTransactionId() == null) {
            event.setTransactionId(UUID.randomUUID().toString());
        }
        if (event.getTimestamp() == null) {
            event.setTimestamp(LocalDateTime.now());
        }
        
        kafkaTemplate.send("transactions", event.getAccountId(), event);
        return "Transaction published to Kafka: " + event.getTransactionId();
    }
}
