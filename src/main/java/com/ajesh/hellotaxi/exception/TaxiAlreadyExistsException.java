package com.ajesh.hellotaxi.exception;

public class TaxiAlreadyExistsException extends RuntimeException {
    public TaxiAlreadyExistsException(String message) {
        super(message);
    }
}