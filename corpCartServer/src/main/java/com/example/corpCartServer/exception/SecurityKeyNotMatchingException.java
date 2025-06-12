package com.example.corpCartServer.exception;

public class SecurityKeyNotMatchingException extends RuntimeException {
    public SecurityKeyNotMatchingException(String message) {
        super(message);
    }
}