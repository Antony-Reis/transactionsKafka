package com.antony.transactions.fraud;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FraudProducer {
    private final KafkaTemplate<String, RNotificationMessageDto> kafkaTemplate;

    public FraudProducer(KafkaTemplate<String, RNotificationMessageDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RNotificationMessageDto message){
        kafkaTemplate.send("notification", LocalDateTime.now().toString(), message);
    }
}
