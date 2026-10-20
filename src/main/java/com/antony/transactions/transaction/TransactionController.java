package com.antony.transactions.transaction;

import com.antony.transactions.transaction.utils.RTransactionRequestDto;
import com.antony.transactions.utils.RMessageDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transaction")
@Validated
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RMessageDto postTransaction(@RequestBody @Valid RTransactionRequestDto body){
        return transactionService.postTransaction(body);
    }
}
