package com.ust.transaction_service.controller;

import com.ust.transaction_service.dto.ApiResponse;
import com.ust.transaction_service.entity.Transaction;
import com.ust.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ApiResponse<Transaction> createTransaction(@RequestBody Transaction transaction) {
        // Logic to create a transaction and send a notification
        boolean result = transactionService.createTransaction(transaction);
        if (result) {
            return new ApiResponse<>(true, "Transaction created successfully", transaction);
        } else {
            return new ApiResponse<>(false, "Failed to create transaction", null);
        }
    }
}
