package com.devsu.mstarjeta.exception;

public class InvalidBalanceException extends RuntimeException {
    public InvalidBalanceException() {
        super();
    }

    public InvalidBalanceException(String message) {
        super(message);
    }
}
