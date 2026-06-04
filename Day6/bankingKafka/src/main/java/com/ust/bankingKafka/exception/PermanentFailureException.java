package com.ust.bankingKafka.exception;

public class PermanentFailureException extends RuntimeException {
    public PermanentFailureException(String message) {
        super(message);
    }
}
