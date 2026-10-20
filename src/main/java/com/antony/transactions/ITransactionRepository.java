package com.antony.transactions;

import com.antony.transactions.transaction.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITransactionRepository extends MongoRepository<TransactionEntity, String> {
}
