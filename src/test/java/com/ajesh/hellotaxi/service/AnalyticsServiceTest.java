package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyticsServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBookingAnalytics_ShouldReturnAnalyticsData() {
        when(bookingRepository.getTotalBookings()).thenReturn(100L);
        when(bookingRepository.getBookingsByStatus(BookingStatus.COMPLETED)).thenReturn(80L);
        when(bookingRepository.getBookingsByStatus(BookingStatus.CANCELLED)).thenReturn(20L);

        List<Object[]> mockBookingsPerDay = new ArrayList<>();
        mockBookingsPerDay.add(new Object[]{"2025-03-30", 5});
        when(bookingRepository.getBookingsPerDay()).thenReturn(mockBookingsPerDay);

        List<Object[]> mockActiveTaxis = new ArrayList<>();
        mockActiveTaxis.add(new Object[]{1L, 20});
        when(bookingRepository.getMostActiveTaxis()).thenReturn(mockActiveTaxis);

        Map<String, Object> analytics = analyticsService.getBookingAnalytics();

        assertEquals(100L, analytics.get("totalBookings"));
        assertEquals(80L, analytics.get("completedBookings"));
        assertEquals(20L, analytics.get("canceledBookings"));
        assertNotNull(analytics.get("bookingsPerDay"));
        assertNotNull(analytics.get("mostActiveTaxis"));

        verify(bookingRepository, times(1)).getTotalBookings();
        verify(bookingRepository, times(1)).getBookingsByStatus(BookingStatus.COMPLETED);
        verify(bookingRepository, times(1)).getBookingsByStatus(BookingStatus.CANCELLED);
        verify(bookingRepository, times(1)).getBookingsPerDay();
        verify(bookingRepository, times(1)).getMostActiveTaxis();
    }
}
