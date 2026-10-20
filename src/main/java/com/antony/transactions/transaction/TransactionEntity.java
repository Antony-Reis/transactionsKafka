package com.antony.transactions.transaction;

import com.antony.transactions.transaction.utils.ETransactionStatusTypes;
import org.springframework.data.annotation.Id;
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

    public TransactionEntity(BigDecimal value, LocalDateTime createAt, ETransactionStatusTypes status) {
        this.value = value;
        this.createAt = createAt;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public ETransactionStatusTypes getStatus() {
        return status;
    }

    public void setStatus(ETransactionStatusTypes status) {
        this.status = status;
    }
}
