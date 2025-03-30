package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.broker.BookingBroker;
import com.ajesh.hellotaxi.model.Taxi;
import com.ajesh.hellotaxi.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaxiServiceTest {

    @Mock
    private TaxiRepository taxiRepository;

    @Mock
    private BookingBroker bookingBroker;

    @InjectMocks
    private TaxiService taxiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTaxis_ShouldReturnListOfTaxis() {
        Taxi taxi1 = new Taxi();
        taxi1.setId(1L);
        Taxi taxi2 = new Taxi();
        taxi2.setId(2L);

        when(taxiRepository.findAll()).thenReturn(Arrays.asList(taxi1, taxi2));

        List<Taxi> result = taxiService.getAllTaxis();

        assertEquals(2, result.size());
        verify(taxiRepository, times(1)).findAll();
    }

    @Test
    void addTaxi_ShouldSaveTaxiAndRegisterWithBookingBroker() {
        Taxi taxi = new Taxi();
        taxi.setId(1L);

        when(taxiRepository.save(any(Taxi.class))).thenReturn(taxi);

        Taxi result = taxiService.addTaxi(taxi);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taxiRepository, times(1)).save(taxi);

        ArgumentCaptor<Consumer<Long>> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);
        verify(bookingBroker, times(1)).registerTaxi(eq(1L), consumerCaptor.capture());
        assertNotNull(consumerCaptor.getValue());
    }

    @Test
    void updateStatus_ShouldReturnTrueWhenUpdated() {
        Taxi taxi = new Taxi();
        taxi.setId(1L);

        when(taxiRepository.updateTaxiStatus(1L, taxi.getStatus())).thenReturn(1);

        boolean result = taxiService.updateStatus(taxi);

        assertTrue(result);
        verify(taxiRepository, times(1)).updateTaxiStatus(1L, taxi.getStatus());
    }

    @Test
    void updateStatus_ShouldReturnFalseWhenNoRowsUpdated() {
        Taxi taxi = new Taxi();
        taxi.setId(1L);

        when(taxiRepository.updateTaxiStatus(1L, taxi.getStatus())).thenReturn(0);

        boolean result = taxiService.updateStatus(taxi);

        assertFalse(result);
        verify(taxiRepository, times(1)).updateTaxiStatus(1L, taxi.getStatus());
    }
}
