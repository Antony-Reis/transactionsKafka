package com.antony.transactions.fraud;

import com.antony.transactions.transaction.utils.ETransactionStatusTypes;

public record RNotificationMessageDto(ETransactionStatusTypes status) {
    String RNotificationMessageDto(ETransactionStatusTypes status){
        return "A transação foi" + status.toString();
    }
}
