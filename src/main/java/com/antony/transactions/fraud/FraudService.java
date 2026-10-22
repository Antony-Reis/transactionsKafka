package com.antony.transactions.fraud;

import com.antony.transactions.transaction.ITransactionRepository;
import com.antony.transactions.transaction.TransactionEntity;
import com.antony.transactions.transaction.utils.ETransactionStatusTypes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FraudService {
    private final ITransactionRepository iTransactionRepository;
    private final FraudProducer fraudProducer;

    public FraudService(ITransactionRepository iTransactionRepository, FraudProducer fraudProducer) {
        this.iTransactionRepository = iTransactionRepository;
        this.fraudProducer = fraudProducer;
    }

    @Transactional
    @KafkaListener(topics = "fraud", groupId = "transactions")
    public void consumeTransaction(TransactionEntity event){
        //Regras de negocio - Bloquear transações maiores do que 5000
        if (event.getValue().compareTo(BigDecimal.valueOf(5000)) > 0){
            TransactionEntity transaction = iTransactionRepository.findById(event.getId()).orElse(null);
            transaction.setStatus(ETransactionStatusTypes.BLOQUEADO);
            iTransactionRepository.save(transaction);

            fraudProducer.sendMessage(new RNotificationMessageDto(ETransactionStatusTypes.BLOQUEADO) );
            return;
        }
        // Verifica se houve 3 ou mais transações no ultimo 1 minuto
        if (iTransactionRepository.countByCreateAtGreaterThanEqual(LocalDateTime.now().minusMinutes(1)) >= 3){
            TransactionEntity transaction = iTransactionRepository.findById(event.getId()).orElse(null);
            transaction.setStatus(ETransactionStatusTypes.BLOQUEADO);
            iTransactionRepository.save(transaction);

            fraudProducer.sendMessage(new RNotificationMessageDto(ETransactionStatusTypes.BLOQUEADO) );
            return;
        }

        //... outras regras de negocio
        TransactionEntity transaction = iTransactionRepository.findById(event.getId()).orElse(null);
        transaction.setStatus(ETransactionStatusTypes.ACEITO);
        iTransactionRepository.save(transaction);
        fraudProducer.sendMessage(new RNotificationMessageDto(ETransactionStatusTypes.ACEITO) );
    }
}
