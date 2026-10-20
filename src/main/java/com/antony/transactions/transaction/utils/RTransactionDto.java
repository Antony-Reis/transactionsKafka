package com.antony.transactions.transaction.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RTransactionDto(BigDecimal value, LocalDateTime createAt, ETransactionStatusTypes status) {
}
