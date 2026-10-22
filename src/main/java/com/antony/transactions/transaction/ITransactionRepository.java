package com.antony.transactions.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface ITransactionRepository extends MongoRepository<TransactionEntity, String> {
    long countByCreateAtGreaterThanEqual(LocalDateTime instant);
}
