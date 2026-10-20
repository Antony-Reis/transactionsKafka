package com.antony.transactions.transaction;

import com.antony.transactions.ITransactionRepository;
import com.antony.transactions.transaction.utils.ETransactionStatusTypes;
import com.antony.transactions.transaction.utils.RTransactionDto;
import com.antony.transactions.transaction.utils.RTransactionRequestDto;
import com.antony.transactions.utils.RMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        , transactionDto.createAt(), ETransactionStatusTypes.PENDENTE);

        iTransactionRepository.save(transactionEntity);

        transactionProducer.sendTransaction(new RTransactionDto(transactionEntity.getValue()
                , transactionEntity.getCreateAt(), transactionEntity.getStatus()));
        System.out.println("Sending: " + transactionEntity);

        return new RMessageDto(HttpStatus.CREATED, "Transaction completed successfully");
    }
}
