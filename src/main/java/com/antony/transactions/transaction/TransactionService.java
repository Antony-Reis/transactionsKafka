package com.antony.transactions.transaction;

import com.antony.transactions.transaction.utils.ETransactionStatusTypes;
import com.antony.transactions.transaction.utils.RTransactionRequestDto;
import com.antony.transactions.utils.RMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionProducer transactionProducer;
    private final ITransactionRepository iTransactionRepository;

    public TransactionService(TransactionProducer transactionProducer, ITransactionRepository iTransactionRepository) {
        this.transactionProducer = transactionProducer;
        this.iTransactionRepository = iTransactionRepository;
    }

    @Transactional
    public RMessageDto postTransaction(RTransactionRequestDto transactionDto){
        TransactionEntity transactionEntity = new TransactionEntity(transactionDto.value()
        , LocalDateTime.now(), ETransactionStatusTypes.PENDENTE);

        iTransactionRepository.save(transactionEntity);

        transactionProducer.sendTransaction(transactionEntity);

        return new RMessageDto(HttpStatus.CREATED, "Transaction completed successfully");
    }
}
