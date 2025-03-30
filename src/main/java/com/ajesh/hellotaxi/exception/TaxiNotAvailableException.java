package com.ajesh.hellotaxi.exception;

public class TaxiNotAvailableException extends RuntimeException {
    public TaxiNotAvailableException(String message) {
        super(message);
    }
}
