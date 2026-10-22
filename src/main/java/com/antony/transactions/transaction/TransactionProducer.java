package com.antony.transactions.transaction;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {
    private final KafkaTemplate<String, TransactionEntity> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
   public void sendTransaction(TransactionEntity event){
        kafkaTemplate.send("fraud", event.getId(), event);
   }
}
