package com.antony.transactions.transaction.utils;

public enum ETransactionStatusTypes {
    PENDENTE("Pendente"),
    BLOQUEADO("Bloqueada"),
    ACEITO("Aceita");

    private final String status;

    ETransactionStatusTypes(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
