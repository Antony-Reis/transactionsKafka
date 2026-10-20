package com.antony.transactions.transaction;

import com.antony.transactions.transaction.utils.ETransactionStatusTypes;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transaction")
public class TransactionEntity {
    @Id
    private String id;

    private BigDecimal value;

    private LocalDateTime createAt;

    private ETransactionStatusTypes status;
}
