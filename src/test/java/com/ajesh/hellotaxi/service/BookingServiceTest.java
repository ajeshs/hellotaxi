package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.enums.TaxiStatus;
import com.ajesh.hellotaxi.model.Booking;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.BookingRepository;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingBroker bookingBroker;

    @Mock
    private TaxiRepository taxiRepository;

    @InjectMocks
    private BookingService bookingService;

    private Booking booking;
    private Taxi taxi;

    @BeforeEach
    void setUp() {
        booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.INITIATED);

        taxi = new Taxi();
        taxi.setId(1L);
        taxi.setStatus(TaxiStatus.AVAILABLE);
    }

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        List<Booking> result = bookingService.getAllBookings();
        assertEquals(1, result.size());
        assertEquals(booking, result.get(0));
    }

    @Test
    void testCreateBooking() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        Booking createdBooking = bookingService.createBooking(new Booking());
        assertNotNull(createdBooking);
        assertEquals(BookingStatus.INITIATED, createdBooking.getStatus());
        verify(bookingBroker).publishBooking(anyLong(), any());
    }

    @Test
    void testAcceptBooking_Success() {
        Taxi taxi = new Taxi();
        taxi.setId(1L);
        taxi.setStatus(TaxiStatus.AVAILABLE);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setTaxi(taxi);
        booking.setStatus(BookingStatus.INITIATED);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(taxiRepository.findById(1L)).thenReturn(Optional.of(taxi));
        boolean result = bookingService.acceptBooking(booking);
        assertTrue(result);
        assertEquals(BookingStatus.ACCEPTED, booking.getStatus());
        assertEquals(TaxiStatus.BOOKED, taxi.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void testAcceptBooking_Failure() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        boolean result = bookingService.acceptBooking(booking);
        assertFalse(result);
    }

    @Test
    void testPickupBooking_Success() {
        booking.setStatus(BookingStatus.ACCEPTED);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        boolean result = bookingService.pickupBooking(booking);
        assertTrue(result);
        assertEquals(BookingStatus.PICKEDUP, booking.getStatus());
        verify(bookingRepository).save(booking);
    }

    @Test
    void testCompleteBooking_Success() {
        booking.setStatus(BookingStatus.PICKEDUP);
        booking.setTaxi(taxi);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        boolean result = bookingService.completeBooking(booking);
        assertTrue(result);
        assertEquals(BookingStatus.COMPLETED, booking.getStatus());
        assertEquals(TaxiStatus.AVAILABLE, taxi.getStatus());
        verify(bookingRepository).save(booking);
        verify(taxiRepository).save(taxi);
    }
}
