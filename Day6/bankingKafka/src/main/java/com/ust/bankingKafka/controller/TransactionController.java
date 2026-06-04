package com.ust.bankingKafka.controller;

import com.ust.bankingKafka.model.Transaction;
import com.ust.bankingKafka.producer.TransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionProducer producer;

    @PostMapping("/success")
    public String success(@RequestBody Transaction transaction) {
        producer.sendTransaction(transaction);
        return "Success Event Sent";
    }

    @PostMapping("/transient-fail")
    public String transientFail(@RequestBody Transaction transaction) {
        producer.sendTransaction(transaction);
        return "Transient Event Sent";
    }

    @PostMapping("/permanent-fail")
    public String permanentFail(@RequestBody Transaction transaction) {
        producer.sendTransaction(transaction);
        return "Permanent Event Sent";
    }
}