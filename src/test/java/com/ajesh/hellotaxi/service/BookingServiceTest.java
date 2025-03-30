package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.model.Booking;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingBroker bookingBroker;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        booking.setStatus(BookingStatus.INITIATED);

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        Booking savedBooking = bookingService.createBooking(booking);

        assertNotNull(savedBooking);
        assertEquals(BookingStatus.INITIATED, savedBooking.getStatus());
    }

//    @Test
//    void testAcceptBooking() {
//        Booking booking = new Booking();
//        booking.setId(1L);
//        booking.setStatus(BookingStatus.INITIATED);
//        Taxi taxi = new Taxi();
//        booking.setTaxi(taxi);
//
//        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
//        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
//
//        boolean isAccepted = bookingService.acceptBooking(booking);
//
//        assertTrue(isAccepted);
//        assertEquals(BookingStatus.ACCEPTED, booking.getStatus());
//    }

//    @Test
//    void testCompleteBooking() {
//        Booking booking = new Booking();
//        booking.setId(1L);
//        booking.setStatus(BookingStatus.ACCEPTED);
//        Taxi taxi = new Taxi();
//        booking.setTaxi(taxi);
//
//        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
//        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
//
//        boolean isCompleted = bookingService.completeBooking(booking);
//
//        assertTrue(isCompleted);
//        assertEquals(BookingStatus.COMPLETED, booking.getStatus());
//    }
}

