package com.ajesh.hellotaxi.exception;

public class TaxiNotFoundException extends RuntimeException {
    public TaxiNotFoundException(String message) {
        super(message);
    }
}
