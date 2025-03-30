package com.ajesh.hellotaxi.controller;

import com.ajesh.hellotaxi.model.Booking;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.service.BookingService;
import com.ajesh.hellotaxi.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/bookings")
    public List<Booking> getAllBookings(@RequestParam(required = false) String status) {
        return bookingService.getAllBookings();
    }

//    @PostMapping("/bookings/initiate")
//    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
//    }


    @PostMapping("/bookings/initiate")
    public ResponseEntity<Long> createBooking(@RequestBody Booking booking) {
        Long bookingId = bookingService.createBooking(booking).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingId);
    }

    @PatchMapping("/bookings/accept")
    public ResponseEntity<String> acceptBooking(@RequestBody Booking booking) {
        boolean accepted = bookingService.acceptBooking(booking);
        return accepted ? ResponseEntity.status(HttpStatus.OK).body("Booking accepted!") :
                ResponseEntity.status(HttpStatus.CONFLICT).body("Booking already taken!");
    }

    @PatchMapping("/bookings/pickup")
    public ResponseEntity<String> pickupBooking(@RequestBody Booking booking) {
        boolean pickedUp = bookingService.pickupBooking(booking);
        return pickedUp ? ResponseEntity.status(HttpStatus.OK).body("Booking picked up!") :
                ResponseEntity.status(HttpStatus.CONFLICT).body("Booking pick up failed!");
    }

    @PatchMapping("/bookings/complete")
    public ResponseEntity<String> completeBooking(@RequestBody Booking booking) {
        boolean completed = bookingService.completeBooking(booking);
        return completed ? ResponseEntity.status(HttpStatus.OK).body("Booking completed!") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking not found!");
    }
}
