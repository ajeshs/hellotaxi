package com.ajesh.hellotaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex);
    }

    // Handle specific exceptions
    @ExceptionHandler({
            BookingNotFoundException.class,
            TaxiNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ExceptionHandler(BookingAlreadyAcceptedException.class)
    public ResponseEntity<Object> handleBookingAlreadyAcceptedException(BookingAlreadyAcceptedException ex, WebRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }

    @ExceptionHandler(BookingCreationException.class)
    public ResponseEntity<Object> handleBookingCreationException(BookingCreationException ex, WebRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }

    @ExceptionHandler(BookingDispatchException.class)
    public ResponseEntity<Object> handleBookingDispatchException(BookingDispatchException ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(InvalidBookingStateException.class)
    public ResponseEntity<Object> handleInvalidBookingStateException(InvalidBookingStateException ex, WebRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }

    @ExceptionHandler(TaxiAlreadyExistsException.class)
    public ResponseEntity<String> handleTaxiAlreadyExists(TaxiAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TaxiBrokerException.class)
    public ResponseEntity<String> handleTaxiBrokerException(TaxiBrokerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(TaxiNotAvailableException.class)
    public ResponseEntity<Object> handleTaxiNotAvailableException(TaxiNotAvailableException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message, Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
