package com.antony.transactions.transaction;

import com.antony.transactions.transaction.utils.RTransactionDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {
    private final KafkaTemplate<String, RTransactionDto> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, RTransactionDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
   public void sendTransaction(RTransactionDto event){
        kafkaTemplate.send("fraud", event.createAt().toString(), event);
   }
}
