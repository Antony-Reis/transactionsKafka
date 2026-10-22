package com.antony.transactions.notification;

import com.antony.transactions.fraud.RNotificationMessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = "notification", groupId = "notification")
    public void consumeFraud(RNotificationMessageDto event){
        System.out.println(event);
    }
}
