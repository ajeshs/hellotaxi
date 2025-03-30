package com.ajesh.hellotaxi.service;

import com.ajesh.hellotaxi.enums.BookingStatus;
import com.ajesh.hellotaxi.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for fetching analytics related to bookings.
 * Provides statistics such as total bookings and completed bookings,
 * bookings per day, and most active taxis.
 */
@Service
public class AnalyticsService {

    private final BookingRepository bookingRepository;

    public AnalyticsService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Retrieves booking-related analytics, including:
     * - Total number of bookings
     * - Number of completed bookings
     * - Booking trends per day
     * - Most active taxis
     *
     * @return a map containing analytics data
     */
    public Map<String, Object> getBookingAnalytics() {
        Map<String, Object> analytics = new HashMap<>();

        analytics.put("totalBookings", bookingRepository.getTotalBookings());
        analytics.put("completedBookings", bookingRepository.getBookingsByStatus(BookingStatus.COMPLETED));
        analytics.put("canceledBookings", bookingRepository.getBookingsByStatus(BookingStatus.CANCELLED));

        List<Object[]> bookingsPerDay = bookingRepository.getBookingsPerDay();
        analytics.put("bookingsPerDay", bookingsPerDay);

        List<Object[]> activeTaxis = bookingRepository.getMostActiveTaxis();
        analytics.put("mostActiveTaxis", activeTaxis);

        return analytics;
    }
}
