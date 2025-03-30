package com.ajesh.hellotaxi.exception;

public class BookingAlreadyAcceptedException extends RuntimeException {
    public BookingAlreadyAcceptedException(String message) {
        super(message);
    }
}