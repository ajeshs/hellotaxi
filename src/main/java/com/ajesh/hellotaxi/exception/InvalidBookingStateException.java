package com.ajesh.hellotaxi.exception;

public class InvalidBookingStateException extends RuntimeException {
    public InvalidBookingStateException(String message) {
        super(message);
    }
}