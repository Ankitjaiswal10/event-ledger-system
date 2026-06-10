package com.eventledger.account.exception;

public class DuplicateTransactionException
        extends RuntimeException {

    public DuplicateTransactionException(String message) {
        super(message);
    }
}